package controller;

import facade.MyFeedbackFacade;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "DriverCustomerRatingController", urlPatterns = {"/DriverCustomerRatingController"})
public class DriverCustomerRatingController extends HttpServlet {

    @EJB
    private MyFeedbackFacade myFeedbackFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String myStaffID = (String) session.getAttribute("userId");
        try {
            List<Object[]> feedbackList = myFeedbackFacade.findDriverFeedback(myStaffID);

            List<Map<String, Object>> feedbackDetails = new ArrayList<>();
            for (Object[] row : feedbackList) {
                Map<String, Object> feedback = new HashMap<>();
                feedback.put("myCustomerID", row[0]);
                feedback.put("content", row[1]);
                feedback.put("rating", row[2]);
                feedback.put("date", row[3]);
                feedbackDetails.add(feedback);
            }
            request.setAttribute("feedbackList", feedbackDetails);
            request.getRequestDispatcher("driverCustomerRatingPage.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?errorMessage=Error+loading+customer+feedback");
        }
    }

}
