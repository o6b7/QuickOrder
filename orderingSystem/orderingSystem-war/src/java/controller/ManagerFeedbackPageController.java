package controller;

import facade.MyFeedbackFacade;
import model.MyFeedback;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ManagerFeedbackPageController", urlPatterns = {"/ManagerFeedbackPageController"})
public class ManagerFeedbackPageController extends HttpServlet {

    @EJB
    private MyFeedbackFacade myFeedbackFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String searchQuery = request.getParameter("searchQuery");

            List<MyFeedback> feedbackList;

            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                // Search feedback by product ID, staff ID, or customer ID
                feedbackList = myFeedbackFacade.searchFeedback(searchQuery.trim());
            } else {
                // Fetch all feedback
                feedbackList = myFeedbackFacade.findAll();
            }

            request.setAttribute("feedbackList", feedbackList);
            request.getRequestDispatcher("managerFeedbackPage.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("errorPage.jsp?errorMessage=Error+loading+feedback+page");
        }
    }
}
