package controller;

import facade.MyStaffFacade;
import model.MyStaff;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "StaffProfileManagementController", urlPatterns = {"/StaffProfileManagementController"})
public class StaffProfileManagementController extends HttpServlet {

    @EJB
    private MyStaffFacade myStaffFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = (String) request.getSession().getAttribute("userId"); // Retrieve userId from session

        if (userId == null) {
            response.sendRedirect("login.jsp"); // Redirect to login if session expired
            return;
        }

        MyStaff staff = myStaffFacade.find(userId);
        if (staff == null) {
            request.setAttribute("errorMessage", "Staff not found.");
            request.getRequestDispatcher("staffProfileManagement.jsp").forward(request, response);
        } else {
            request.setAttribute("staff", staff);
            request.getRequestDispatcher("staffProfileManagement.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = (String) request.getSession().getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        MyStaff staff = myStaffFacade.find(userId);
        if (staff == null) {
            request.setAttribute("errorMessage", "Staff not found.");
            doGet(request, response); // Reload page with error message
            return;
        }
        String currentPassword = request.getParameter("currentPassword");
        if (!staff.getPassword().equals(currentPassword)) {
            request.setAttribute("errorMessage", "Incorrect current password.");
            doGet(request, response); // Reload page with error message
            return;
        }
        staff.setName(request.getParameter("name"));
        staff.setEmail(request.getParameter("email"));
        staff.setPhone(request.getParameter("phone"));
        staff.setIC(request.getParameter("IC"));
        staff.setAddress(request.getParameter("address"));
        String newPassword = request.getParameter("newPassword");
        if (newPassword != null && !newPassword.trim().isEmpty()) {
            staff.setPassword(newPassword);
        }
        try {
            myStaffFacade.edit(staff);
            request.setAttribute("successMessage", "Profile updated successfully.");
        } catch (Exception e) {
            request.setAttribute("errorMessage", "An error occurred while updating the profile.");
        }

        doGet(request, response); // Reload page with updated data
    }
}
