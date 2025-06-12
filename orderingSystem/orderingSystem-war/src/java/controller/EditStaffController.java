package controller;

import facade.MyStaffFacade;
import model.MyStaff;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Edit Staff Profile Controller
 */
@WebServlet(name = "EditStaffController", urlPatterns = {"/EditStaffController"})
public class EditStaffController extends HttpServlet {

    @EJB
    private MyStaffFacade myStaffFacade;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Retrieve form parameters
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String role = request.getParameter("role");
            String gender = request.getParameter("gender");
            String IC = request.getParameter("IC");
            String address = request.getParameter("address");
            String password = request.getParameter("password");

            if (id == null || id.trim().isEmpty()) {
                throw new Exception("Staff ID is missing.");
            }

            // Find the staff member by ID
            MyStaff staff = myStaffFacade.find(id);
            if (staff == null) {
                throw new Exception("Staff not found.");
            }

            // Update staff details
            staff.setName(name);
            staff.setEmail(email);
            staff.setPhone(phone);
            staff.setRole(role);
            staff.setGender(gender);
            staff.setIC(IC);
            staff.setAddress(address);

            // Only update the password if provided
            if (password != null && !password.trim().isEmpty()) {
                staff.setPassword(password);
            }

            // Persist the updated staff
            myStaffFacade.edit(staff);

            // Redirect to staff management page with success message
            response.sendRedirect("StaffManagementController?successMessage=Staff+profile+updated+successfully");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error updating staff profile: " + e.getMessage());
            request.getRequestDispatcher("editStaffProfile.jsp").forward(request, response);
        }
    }
}
