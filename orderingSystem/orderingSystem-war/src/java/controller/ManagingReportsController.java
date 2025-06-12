package controller;

import facade.MyCustomerFacade;
import facade.MyFeedbackFacade;
import facade.MyOrderFacade;
import facade.MyProductFacade;
import facade.MyStaffFacade;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ManagingReportsController", urlPatterns = {"/ManagingReportsController"})
public class ManagingReportsController extends HttpServlet {

    @EJB
    private MyProductFacade myProductFacade;

    @EJB
    private MyCustomerFacade myCustomerFacade;

    @EJB
    private MyStaffFacade myStaffFacade;

    @EJB
    private MyOrderFacade myOrderFacade;

    @EJB
    private MyFeedbackFacade myFeedbackFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Fetch data from facades
            long totalProducts = myProductFacade.countTotalProducts();
            long totalCustomers = myCustomerFacade.countTotalCustomers();
            long femaleCustomers = myCustomerFacade.countFemaleCustomers();
            long maleCustomers = myCustomerFacade.countMaleCustomers();
            long deliveryStaff = myStaffFacade.countDeliveryStaff();
            long managingStaff = myStaffFacade.countManagingStaff();
            long totalOrders = myOrderFacade.countTotalOrders();
            long driverFeedbacks = myFeedbackFacade.countDriverFeedbacks();
            long productFeedbacks = myFeedbackFacade.countProductFeedbacks();

            // Set attributes for the JSP
            request.setAttribute("totalProducts", totalProducts);
            request.setAttribute("totalCustomers", totalCustomers);
            request.setAttribute("femaleCustomers", femaleCustomers);
            request.setAttribute("maleCustomers", maleCustomers);
            request.setAttribute("deliveryStaff", deliveryStaff);
            request.setAttribute("managingStaff", managingStaff);
            request.setAttribute("totalOrders", totalOrders);
            request.setAttribute("driverFeedbacks", driverFeedbacks);
            request.setAttribute("productFeedbacks", productFeedbacks);

            // Forward to the JSP
            request.getRequestDispatcher("managerReports.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
