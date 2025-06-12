package controller;

import facade.MyFeedbackFacade;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.MyFeedback;

@WebServlet(name = "CustomerRateDriverController", urlPatterns = {"/CustomerRateDriverController"})
public class CustomerRateDriverController extends HttpServlet {

    @EJB
    private MyFeedbackFacade myFeedbackFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);

            if (session == null || session.getAttribute("userId") == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            String myStaffID = request.getParameter("myStaffID");
            String myProductID = request.getParameter("myProductID");
            String myCustomerID = (String) session.getAttribute("userId");

            // Check if feedback already exists for this driver and order
            Map<String, Object> existingFeedback = myFeedbackFacade.findDriverFeedback(myStaffID, myProductID, myCustomerID);

            if (existingFeedback != null) {
                response.sendRedirect("CustomerOrdersController?errorMessage=You+have+already+rated+this+driver.");
                return;
            }

            // Set attributes for the rate driver page
            request.setAttribute("myStaffID", myStaffID);
            request.setAttribute("myProductID", myProductID);
            request.setAttribute("myCustomerID", myCustomerID);

            // Forward to the rate driver page
            request.getRequestDispatcher("customerRateDriver.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("CustomerOrdersController?errorMessage=Error+loading+rate+driver+page");
        }
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            String myStaffID = request.getParameter("myStaffID");
            String myProductID = request.getParameter("myProductID");
            String dateParam = request.getParameter("date");
            LocalDate date = (dateParam == null || dateParam.isEmpty()) ? LocalDate.now() : LocalDate.parse(dateParam);
            String myCustomerID = (String) session.getAttribute("userId");
            String content = request.getParameter("content");
            int rating = Integer.parseInt(request.getParameter("rating"));

            // Validate input data
            if (myStaffID == null || myStaffID.isEmpty()) {
                throw new Exception("Staff ID is missing");
            }
            if (myProductID == null || myProductID.isEmpty()) {
                throw new Exception("Product ID is missing");
            }
            if (content == null || content.isEmpty()) {
                throw new Exception("Content is missing");
            }
            if (rating < 1 || rating > 5) {
                throw new Exception("Rating must be between 1 and 5");
            }

            // Check if feedback already exists
            Map<String, Object> existingFeedback = myFeedbackFacade.findDriverFeedback(myStaffID, myProductID, myCustomerID);

            if (existingFeedback != null) {
                response.sendRedirect("CustomerOrdersController?errorMessage=You+have+already+rated+this+driver.");
                return;
            }

            // Generate feedback ID
            String feedbackId = myFeedbackFacade.generateNextFeedbackId();

            // Create feedback object
            MyFeedback feedback = new MyFeedback();
            feedback.setMyFeedbackID(feedbackId);
            feedback.setMyStaffID(myStaffID);
            feedback.setMyProductID(myProductID);
            feedback.setMyCustomerID(myCustomerID);
            feedback.setContent(content);
            feedback.setRating(rating);
            feedback.setType("driver");
            feedback.setDate(date);

            // Persist feedback
            myFeedbackFacade.create(feedback);

            response.sendRedirect("CustomerOrdersController?successMessage=Driver+feedback+submitted+successfully");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("CustomerOrdersController?errorMessage=Error+submitting+driver+feedback");
        }
    }





}
