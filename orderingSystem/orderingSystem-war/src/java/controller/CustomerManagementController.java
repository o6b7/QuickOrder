package controller;

import facade.MyCustomerFacade;
import model.MyCustomer;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Customer Management Controller
 */
@WebServlet(name = "CustomerManagementController", urlPatterns = {"/CustomerManagementController"})
public class CustomerManagementController extends HttpServlet {

    @EJB
    private MyCustomerFacade myCustomerFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("delete".equalsIgnoreCase(action)) {
                handleDelete(request, response);
            } else if ("edit".equalsIgnoreCase(action)) {
                handleEdit(request, response);
            } else {
                handleList(request, response);
            }
        } catch (Exception e) {
            handleError(request, response, e, "customerManagement.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            handleEditSubmission(request, response);
        } catch (Exception e) {
            handleError(request, response, e, "customerEditProfile.jsp");
        }
    }

    private void handleList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String search = request.getParameter("search");
        List<MyCustomer> customerList;

        if (search == null || search.trim().isEmpty()) {
            customerList = myCustomerFacade.findAll();
        } else {
            customerList = myCustomerFacade.findBySearchTerm(search.trim());
        }

        request.setAttribute("customerList", customerList);
        request.getRequestDispatcher("customerManagement.jsp").forward(request, response);
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String id = getRequiredParameter(request, "id", "Customer ID is missing.");
        MyCustomer customer = findCustomerById(id);
        myCustomerFacade.remove(customer);
        response.sendRedirect("CustomerManagementController?successMessage=Customer+successfully+deleted");
    }

    private void handleEdit(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String id = getRequiredParameter(request, "id", "Customer ID is missing.");
        MyCustomer customer = findCustomerById(id);
        request.setAttribute("customer", customer);
        request.getRequestDispatcher("customerEditProfile.jsp").forward(request, response);
    }

    private void handleEditSubmission(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String id = getRequiredParameter(request, "id", "Customer ID is missing.");
        MyCustomer customer = findCustomerById(id);

        customer.setName(request.getParameter("name"));
        customer.setEmail(request.getParameter("email"));
        customer.setPhone(request.getParameter("phone"));
        customer.setGender(request.getParameter("gender"));
        customer.setIC(request.getParameter("IC"));
        customer.setAddress(request.getParameter("address"));

        String password = request.getParameter("password");
        if (password != null && !password.trim().isEmpty()) {
            customer.setPassword(password);
        }

        myCustomerFacade.edit(customer);
        response.sendRedirect("CustomerManagementController?successMessage=Customer+profile+updated+successfully");
    }

    private String getRequiredParameter(HttpServletRequest request, String paramName, String errorMessage)
            throws Exception {
        String paramValue = request.getParameter(paramName);
        if (paramValue == null || paramValue.trim().isEmpty()) {
            throw new Exception(errorMessage);
        }
        return paramValue;
    }

    private MyCustomer findCustomerById(String id) throws Exception {
        MyCustomer customer = myCustomerFacade.find(id);
        if (customer == null) {
            throw new Exception("Customer not found.");
        }
        return customer;
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, Exception e, String jspPage)
            throws ServletException, IOException {
        e.printStackTrace();
        request.setAttribute("errorMessage", "Error: " + e.getMessage());
        request.getRequestDispatcher(jspPage).forward(request, response);
    }
}
