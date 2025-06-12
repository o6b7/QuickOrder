package controller;

import facade.MyStaffFacade;
import model.MyStaff;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Staff Management Controller
 */
@WebServlet(name = "StaffManagementController", urlPatterns = {"/StaffManagementController"})
public class StaffManagementController extends HttpServlet {

    @EJB
    private MyStaffFacade myStaffFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            if ("delete".equalsIgnoreCase(action)) {
                handleDelete(request, response);
                return;
            } else if ("edit".equalsIgnoreCase(action)) {
                handleEdit(request, response);
                return;
            }

            // Fetch all staff or search results
            String search = request.getParameter("search");
            List<MyStaff> staffList;

            if (search == null || search.trim().isEmpty()) {
                staffList = myStaffFacade.findAll();
            } else {
                staffList = myStaffFacade.findBySearchTerm(search.trim());
            }

            request.setAttribute("staffList", staffList);
            request.getRequestDispatcher("staffManagement.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading staff: " + e.getMessage());
            request.getRequestDispatcher("staffManagement.jsp").forward(request, response);
        }
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String id = request.getParameter("id");
            MyStaff staff = myStaffFacade.find(id);

            if (staff != null) {
                myStaffFacade.remove(staff);
                response.sendRedirect("StaffManagementController?successMessage=Staff+successfully+deleted");
            } else {
                throw new Exception("Staff not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error deleting staff: " + e.getMessage());
            request.getRequestDispatcher("staffManagement.jsp").forward(request, response);
        }
    }

    private void handleEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String id = request.getParameter("id");
            MyStaff staff = myStaffFacade.find(id);

            if (staff != null) {
                request.setAttribute("staff", staff);
                request.getRequestDispatcher("editStaffProfile.jsp").forward(request, response);
            } else {
                throw new Exception("Staff not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading staff for edit: " + e.getMessage());
            request.getRequestDispatcher("staffManagement.jsp").forward(request, response);
        }
    }
}
