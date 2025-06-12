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
import javax.servlet.http.HttpSession;

/**
 * Servlet for editing customer information, accessible by both customers and managing staff
 */
@WebServlet(name = "CustomerEditController", urlPatterns = {"/CustomerEditController"})
public class CustomerEditController extends HttpServlet {

    @EJB
    private MyCustomerFacade myCustomerFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get the userId from the session
            HttpSession session = request.getSession();
            String userId = (String) session.getAttribute("userId");

            if (userId == null || userId.trim().isEmpty()) {
                // Redirect to login if userId is not in session
                response.sendRedirect("login.jsp");
                return;
            }

            // Retrieve the customer using the userId
            MyCustomer customer = myCustomerFacade.find(userId);

            if (customer == null) {
                throw new Exception("Customer not found.");
            }

            // Set customer as a request attribute
            request.setAttribute("customer", customer);

            // Forward to the JSP
            request.getRequestDispatcher("customerEditProfile.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while loading the profile: " + e.getMessage());
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
        }
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String email = request.getParameter("email"); // Use email from the request parameter

            if (email == null || email.trim().isEmpty()) {
                throw new Exception("Email is required to update customer profile.");
            }

            MyCustomer customer = myCustomerFacade.findByEmail(email); // Assuming a method to find customer by email
            if (customer == null) {
                throw new Exception("Customer not found.");
            }

            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String gender = request.getParameter("gender");
            String IC = request.getParameter("IC");
            String address = request.getParameter("address");
            String password = request.getParameter("password");

            customer.setName(name);
            customer.setPhone(phone);
            customer.setGender(gender);
            customer.setIC(IC);
            customer.setAddress(address);

            if (password != null && !password.trim().isEmpty()) {
                customer.setPassword(password);
            }

            myCustomerFacade.edit(customer);

            String userRole = request.getParameter("userRole");
            String redirectPage = "Managing Staff".equals(userRole) ? "customerManagement.jsp" : "welcomePage.jsp";
            response.sendRedirect(redirectPage);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error updating customer profile: " + e.getMessage());
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
        }
    }

}
