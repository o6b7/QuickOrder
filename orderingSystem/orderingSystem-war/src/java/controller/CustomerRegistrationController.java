package controller;

import facade.MyCustomerFacade;
import facade.MyStaffFacade;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.MyCustomer;

/**
 * Servlet for handling customer registration.
 */
@WebServlet(name = "CustomerRegistrationController", urlPatterns = {"/CustomerRegistrationController"})
public class CustomerRegistrationController extends HttpServlet {

    @EJB
    private MyCustomerFacade myCustomerFacade;

    @EJB
    private MyStaffFacade myStaffFacade;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String gender = request.getParameter("gender");
        String IC = request.getParameter("IC");

        // Validate input
        String validationError = validateInput(name, email, password, phone, address, gender, IC);
        if (validationError != null) {
            // Stay on the same page and show the error
            request.setAttribute("errorMessage", validationError);
            request.setAttribute("name", name);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.setAttribute("address", address);
            request.setAttribute("gender", gender);
            request.setAttribute("IC", IC);
            request.getRequestDispatcher("customerRegistration.jsp").forward(request, response);
            return;
        }

        // Check if the email is already registered
        if (isEmailRegisteredInCustomer(email) || isEmailRegisteredInStaff(email)) {
            request.setAttribute("errorMessage", "The email \"" + email + "\" is already registered.");
            request.getRequestDispatcher("customerRegistration.jsp").forward(request, response);
            return;
        }

        try {
            // Generate the next customer ID
            String nextId = fetchNextCustomerId();

            // Create a new MyCustomer object
            MyCustomer customer = new MyCustomer();
            customer.setId(nextId);
            customer.setName(name);
            customer.setEmail(email);
            customer.setPassword(password);
            customer.setPhone(phone);
            customer.setAddress(address);
            customer.setGender(gender);
            customer.setIC(IC);

            // Save the customer using the facade
            myCustomerFacade.create(customer);

            // Store user details in the session
            HttpSession session = request.getSession();
            session.setAttribute("userId", nextId);
            session.setAttribute("userName", name);
            session.setAttribute("userRole", "Customer");

            // Redirect to welcome page
            response.sendRedirect("welcomePage.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error during registration: " + e.getMessage());
            request.getRequestDispatcher("registration.jsp").forward(request, response);
        }
    }

    private String validateInput(String name, String email, String password, String phone, String address, String gender, String IC) {
        if (name == null || name.trim().isEmpty()) return "Name is required.";
        if (email == null || !email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) return "Valid email is required.";
        if (password == null || !password.matches("^(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{8,}$")) {
            return "Password must be at least 8 characters long, contain at least one number, and one special character.";
        }
        if (phone == null || !phone.matches("^\\d{9,10}$")) return "Phone number must be 9 to 10 digits.";
        if (address != null && address.length() > 255) return "Address must not exceed 255 characters.";
        if (gender == null || (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female"))) return "Gender must be Male or Female.";
        if (IC == null || IC.trim().isEmpty()) return "Identification Card (IC) is required.";
        return null;
    }

    private boolean isEmailRegisteredInCustomer(String email) {
        try {
            String query = "SELECT COUNT(c) FROM MyCustomer c WHERE c.email = :email";
            Long count = (Long) myCustomerFacade.getEntityManagerForController()
                    .createQuery(query)
                    .setParameter("email", email)
                    .getSingleResult();

            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isEmailRegisteredInStaff(String email) {
        try {
            String query = "SELECT COUNT(c) FROM MyStaff c WHERE c.email = :email";
            Long count = (Long) myStaffFacade.getEntityManagerForController()
                    .createQuery(query)
                    .setParameter("email", email)
                    .getSingleResult();

            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String fetchNextCustomerId() {
        try {
            String query = "SELECT MAX(c.id) FROM MyCustomer c";
            String lastId = (String) myCustomerFacade.getEntityManagerForController()
                    .createQuery(query)
                    .getSingleResult();

            if (lastId != null) {
                int numericPart = Integer.parseInt(lastId.substring(1));
                return String.format("C%03d", numericPart + 1);
            } else {
                return "C001";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "C001";
        }
    }

    @Override
    public String getServletInfo() {
        return "Customer Registration Controller Servlet";
    }
}
