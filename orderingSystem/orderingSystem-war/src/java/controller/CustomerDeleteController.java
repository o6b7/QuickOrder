package controller;

import facade.MyCustomerFacade;
import model.MyCustomer;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles customer deletion.
 */
@WebServlet(name = "CustomerDeleteController", urlPatterns = {"/CustomerDeleteController"})
public class CustomerDeleteController extends HttpServlet {

    @EJB
    private MyCustomerFacade myCustomerFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get the customer ID from the request
            String id = request.getParameter("id");

            if (id == null || id.trim().isEmpty()) {
                throw new Exception("Customer ID is missing.");
            }

            // Find the customer by ID
            MyCustomer customer = myCustomerFacade.find(id);

            if (customer != null) {
                // Remove the customer
                myCustomerFacade.remove(customer);
                response.sendRedirect("CustomerManagementController?successMessage=Customer+successfully+deleted");
            } else {
                throw new Exception("Customer not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error deleting customer: " + e.getMessage());
            request.getRequestDispatcher("customerManagement.jsp").forward(request, response);
        }
    }
}
