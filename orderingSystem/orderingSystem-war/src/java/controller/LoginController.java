package controller;

import facade.MyCustomerFacade;
import facade.MyStaffFacade;
import model.MyCustomer;
import model.MyStaff;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    @EJB
    private MyCustomerFacade myCustomerFacade;

    @EJB
    private MyStaffFacade myStaffFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("logout".equalsIgnoreCase(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect("login.jsp"); 
            return;
        }

        HttpSession session = request.getSession(false); 
        if (session != null && session.getAttribute("userRole") != null) {
            response.sendRedirect("welcomePage.jsp");
        }

        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession session = request.getSession(); // Create or retrieve the session
        try {
            MyCustomer customer = findCustomerByEmailAndPassword(email, password);
            if (customer != null) {
                session.setAttribute("userId", customer.getId());
                session.setAttribute("userName", customer.getName());
                session.setAttribute("userRole", "Customer");
                response.sendRedirect("welcomePage.jsp");
                return;
            }
            MyStaff staff = findStaffByEmailAndPassword(email, password);
            if (staff != null) {
                session.setAttribute("userId", staff.getId());
                session.setAttribute("userName", staff.getName());
                session.setAttribute("userRole", staff.getRole());
                response.sendRedirect("welcomePage.jsp");
                return;
            }
            request.setAttribute("errorMessage", "Invalid email or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private MyCustomer findCustomerByEmailAndPassword(String email, String password) {
        try {
            return myCustomerFacade.findCustomerByEmailAndPassword(email, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private MyStaff findStaffByEmailAndPassword(String email, String password) {
        try {
            return myStaffFacade.findStaffByEmailAndPassword(email, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
