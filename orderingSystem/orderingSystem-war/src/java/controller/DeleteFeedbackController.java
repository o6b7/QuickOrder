/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import facade.MyFeedbackFacade;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MyFeedback;

@WebServlet(name = "DeleteFeedbackController", urlPatterns = {"/DeleteFeedbackController"})
public class DeleteFeedbackController extends HttpServlet {

    @EJB
    private MyFeedbackFacade myFeedbackFacade;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String feedbackId = request.getParameter("feedbackId");
            MyFeedback feedback = myFeedbackFacade.find(feedbackId);

            if (feedback != null) {
                myFeedbackFacade.remove(feedback);
            }

            response.sendRedirect("CustomerOrdersController?successMessage=Feedback+deleted+successfully");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("CustomerOrdersController?errorMessage=Error+deleting+feedback");
        }
    }
}
