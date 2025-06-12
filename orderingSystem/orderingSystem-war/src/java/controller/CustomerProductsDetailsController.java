package controller;

import facade.MyProductFacade;
import facade.MyFeedbackFacade;
import model.MyProduct;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

@WebServlet(name = "CustomerProductsDetailsController", urlPatterns = {"/CustomerProductsDetailsController"})
public class CustomerProductsDetailsController extends HttpServlet {

    @EJB
    private MyProductFacade myProductFacade;

    @EJB
    private MyFeedbackFacade myFeedbackFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String productId = request.getParameter("id"); // Product ID passed from the JSP
            MyProduct product = myProductFacade.find(productId);
            if (product != null && product.getPicture() != null) {
                String base64Image = Base64.getEncoder().encodeToString(product.getPicture());
                product.setBase64Image(base64Image);
            }
            List<Object[]> feedbackData = myFeedbackFacade.findProductFeedback(productId);
            List<Map<String, Object>> feedbackList = feedbackData.stream().map(row -> {
                Map<String, Object> feedbackMap = new HashMap<>();
                feedbackMap.put("customerName", row[0]);
                feedbackMap.put("content", row[1]);
                feedbackMap.put("rating", row[2]);
                feedbackMap.put("date", row[3]);
                return feedbackMap;
            }).collect(Collectors.toList());
            request.setAttribute("product", product);
            request.setAttribute("feedbackList", feedbackList);
            request.getRequestDispatcher("customerProductsDetails.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error retrieving product details: " + e.getMessage());
            response.sendRedirect("customerOrdersPage.jsp");
        }
    }

}
