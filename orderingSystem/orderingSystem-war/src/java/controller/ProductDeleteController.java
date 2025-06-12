package controller;

import facade.MyProductFacade;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MyProduct;
import java.io.IOException;

/**
 * Handles product deletion.
 */
@WebServlet(name = "ProductDeleteController", urlPatterns = {"/ProductDeleteController"})
public class ProductDeleteController extends HttpServlet {

    @EJB
    private MyProductFacade myProductFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Fetch product ID as a String
            String id = request.getParameter("id");
            
            // Find the product using the ID
            MyProduct product = myProductFacade.find(id);
            
            if (product != null) {
                // Remove the product if it exists
                myProductFacade.remove(product);
                response.sendRedirect("ProductManagementController?successMessage=Product+deleted+successfully");
            } else {
                throw new Exception("Product not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error deleting product: " + e.getMessage());
            request.getRequestDispatcher("productManagement.jsp").forward(request, response);
        }
    }
}
