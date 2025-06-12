package controller;

import facade.MyFeedbackFacade;
import facade.MyOrderFacade;
import model.MyFeedback;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "CustomerFeedbackController", urlPatterns = {"/CustomerFeedbackController"})
public class CustomerFeedbackController extends HttpServlet {
    @EJB
    private MyFeedbackFacade myFeedbackFacade;
    @EJB
    private MyOrderFacade myOrderFacade;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Retrieve parameters from the request
            String productId = request.getParameter("productId");
            String content = request.getParameter("content");
            int rating = Integer.parseInt(request.getParameter("rating"));
            String customerId = (String) request.getSession().getAttribute("userId");

            // Check if the user is logged in
            if (customerId == null) {
                out.print("<div class='error-message'>You must be logged in to submit feedback.</div>");
                return;
            }

            // Verify if the customer has ordered the product
            boolean hasOrdered = myOrderFacade.hasCustomerOrderedProduct(customerId, productId);

            if (hasOrdered) {
                // Generate a new feedback ID
                String feedbackId = myFeedbackFacade.generateNextFeedbackId();

                // Create a new feedback object
                MyFeedback feedback = new MyFeedback();
                feedback.setMyFeedbackID(feedbackId);
                feedback.setMyProductID(productId);
                feedback.setMyCustomerID(customerId);
                feedback.setContent(content);
                feedback.setRating(rating);
                feedback.setType("product");
                feedback.setDate(java.time.LocalDate.now());

                // Save the feedback using the facade
                myFeedbackFacade.create(feedback);

                // Fetch the updated feedback list for the product
                List<Object[]> feedbackList = myFeedbackFacade.findProductFeedback(productId);

                // Generate HTML for the updated feedback list
                for (Object[] fb : feedbackList) {
                    out.print("<div class='feedback-item' style='border: 1px solid #ddd; border-radius: 10px; padding: 20px; margin-bottom: 15px;'>");
                    out.print("<div style='display: flex; justify-content: space-between; align-items: center;'>");
                    out.print("<strong>" + fb[1] + "</strong>"); // Assuming index 1 is customer name
                    out.print("<span>" + fb[2] + "</span>"); // Assuming index 2 is date
                    out.print("</div>");
                    out.print("<p style='margin-top: 15px; white-space: pre-wrap;'>" + fb[3] + "</p>"); // Assuming index 3 is content
                    out.print("<p><strong>Rating:</strong> " + fb[4] + " / 5</p>"); // Assuming index 4 is rating
                    out.print("</div>");
                }
            } else {
                out.print("<div class='error-message'>You cannot leave feedback for a product you haven't purchased.</div>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("<div class='error-message'>Error submitting feedback: " + e.getMessage() + "</div>");
        }
    }
}
