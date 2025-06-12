package controller;

import facade.MyCustomerFacade;
import facade.MyStaffFacade;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MyStaff;

@WebServlet(name = "addStaffController", urlPatterns = {"/addStaffController"})
public class addStaffController extends HttpServlet {

    @EJB
    private MyCustomerFacade myCustomerFacade;

    @EJB
    private MyStaffFacade myStaffFacade;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean success = false;
        int retryCount = 0;

        while (!success && retryCount < 2) {
            try {
                retryCount++;
                Logger.getLogger(addStaffController.class.getName()).log(Level.INFO, "Retry Count: {0}", retryCount);

                // Retrieve form data
                String role = request.getParameter("role");
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String phone = request.getParameter("phone");
                String gender = request.getParameter("gender"); // New field
                String IC = request.getParameter("IC");         // New field
                String address = request.getParameter("address"); // New field

                Logger.getLogger(addStaffController.class.getName()).log(Level.INFO, "Received Data - Role: {0}, Name: {1}, Email: {2}, Gender: {3}, IC: {4}, Address: {5}", 
                    new Object[]{role, name, email, gender, IC, address});

                // Check if the email is already registered
                if (isEmailRegisteredInCustomer(email) || isEmailRegisteredInStaff(email)) {
                    request.setAttribute("errorMessage", "Email \"" + email + "\" is already registered.");
                    request.getRequestDispatcher("addStaff.jsp").forward(request, response);
                    return;
                }

                // Validate input
                String validationError = validateInput(name, email, password, phone);
                if (validationError != null) {
                    request.setAttribute("errorMessage", validationError);
                    request.getRequestDispatcher("addStaff.jsp").forward(request, response);
                    return;
                }

                // Generate the next staff ID
                String nextId = generateNextStaffId(role);

                // Create and persist the MyStaff entity
                MyStaff staff = new MyStaff();
                staff.setId(nextId);
                staff.setName(name);
                staff.setEmail(email);
                staff.setPassword(password);
                staff.setPhone(phone);
                staff.setGender(gender); // Set new field
                staff.setIC(IC);         // Set new field
                staff.setAddress(address); // Set new field
                staff.setRole(role);

                myStaffFacade.create(staff);

                Logger.getLogger(addStaffController.class.getName()).log(Level.INFO, "Staff added successfully with ID: {0}", nextId);

                // Redirect to dashboard on success
                request.getRequestDispatcher("welcomePage.jsp").forward(request, response);
                success = true;

            } catch (Exception e) {
                Logger.getLogger(addStaffController.class.getName()).log(Level.SEVERE, "Error during staff addition", e);
                if (retryCount >= 2) {
                    request.setAttribute("errorMessage", "Error adding staff: " + e.getMessage());
                    request.getRequestDispatcher("addStaff.jsp").forward(request, response);
                    return;
                }
            }
        }
    }

    private boolean isEmailRegisteredInCustomer(String email) {
        try {
            String query = "SELECT COUNT(c) FROM MyCustomer c WHERE LOWER(c.email) = LOWER(:email)";
            Long count = (Long) myCustomerFacade.getEntityManagerForController()
                    .createQuery(query)
                    .setParameter("email", email.trim()) // Trim input to prevent false negatives
                    .getSingleResult();
            Logger.getLogger(addStaffController.class.getName())
                  .log(Level.INFO, "Customer email count: {0}", count);
            return count > 0;
        } catch (Exception e) {
            Logger.getLogger(addStaffController.class.getName())
                  .log(Level.SEVERE, "Error checking customer email", e);
            return false; // Fallback to false in case of exception
        }
    }


    private boolean isEmailRegisteredInStaff(String email) {
        try {
            String query = "SELECT COUNT(s) FROM MyStaff s WHERE LOWER(s.email) = LOWER(:email)";
            Long count = (Long) myStaffFacade.getEntityManagerForController()
                    .createQuery(query)
                    .setParameter("email", email.trim()) // Trim input to prevent false negatives
                    .getSingleResult();
            Logger.getLogger(addStaffController.class.getName())
                  .log(Level.INFO, "Staff email count: {0}", count);
            return count > 0;
        } catch (Exception e) {
            Logger.getLogger(addStaffController.class.getName())
                  .log(Level.SEVERE, "Error checking staff email", e);
            return false; // Fallback to false in case of exception
        }
    }
    private String validateInput(String name, String email, String password, String phone) {
        if (name == null || name.trim().isEmpty()) return "Name is required.";
        if (email == null || !email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) return "Valid email is required.";
        if (password == null || password.length() < 8) return "Password must be at least 8 characters.";
        if (phone != null && !phone.matches("^\\d{10,15}$")) return "Phone number must be numeric and 10 to 15 digits.";
        return null;
    }
    private String generateNextStaffId(String role) {
        try {
            String prefix = role.equals("Delivery Staff") ? "D" : "M";
            String query = "SELECT MAX(s.id) FROM MyStaff s WHERE s.id LIKE :prefix";
            String lastId = (String) myStaffFacade.getEntityManagerForController()
                    .createQuery(query)
                    .setParameter("prefix", prefix + "%")
                    .getSingleResult();

            if (lastId != null) {
                String numericPartStr = lastId.substring(prefix.length());
                int numericPart = Integer.parseInt(numericPartStr);
                return String.format(prefix + "%03d", numericPart + 1);
            } else {
                return prefix + "001";
            }
        } catch (Exception e) {
            Logger.getLogger(addStaffController.class.getName()).log(Level.SEVERE, "Error generating staff ID", e);
            return role.equals("Delivery Staff") ? "D001" : "M001";
        }
    }
}
