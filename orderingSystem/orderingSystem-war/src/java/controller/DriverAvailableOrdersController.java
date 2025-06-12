package controller;

import dto.OrderDTO;
import facade.MyCustomerFacade;
import facade.MyOrderFacade;
import facade.MyProductFacade;
import model.MyCustomer;
import model.MyOrder;
import model.MyProduct;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "DriverAvailableOrdersController", urlPatterns = {"/DriverAvailableOrdersController"})
public class DriverAvailableOrdersController extends HttpServlet {

    @EJB
    private MyOrderFacade myOrderFacade;

    @EJB
    private MyCustomerFacade myCustomerFacade;

    @EJB
    private MyProductFacade myProductFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchQuery = request.getParameter("searchQuery");
        String userId = (String) request.getSession().getAttribute("userId"); // Get userId from session

        if (userId == null) {
            response.sendRedirect("login.jsp"); // Redirect to login if session is not set
            return;
        }

        try {
            List<MyOrder> orders;

            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                // Filter orders by search query and staff ID
                orders = myOrderFacade.findOrdersByQuery(searchQuery).stream()
                        .filter(order -> userId.equals(order.getMyStaffID())) // Match staff ID
                        .collect(Collectors.toList());
            } else {
                // Retrieve all orders assigned to the logged-in driver
                orders = myOrderFacade.findAll().stream()
                        .filter(order -> userId.equals(order.getMyStaffID())) // Match staff ID
                        .collect(Collectors.toList());
            }

            // Convert orders to DTOs for the JSP
            List<OrderDTO> orderDTOList = orders.stream()
                    .map(order -> {
                        MyCustomer customer = myCustomerFacade.find(order.getMyCustomerID());
                        MyProduct product = myProductFacade.find(order.getMyProductID());

                        OrderDTO dto = new OrderDTO();
                        dto.setMyOrderId(order.getMyOrderId());
                        dto.setMyCustomerID(order.getMyCustomerID());
                        dto.setMyCustomerName(customer != null ? customer.getName() : "Unknown");
                        dto.setCustomerAddress(customer != null ? customer.getAddress() : "No Address");
                        dto.setMyProductID(order.getMyProductID());
                        dto.setProductPrice(product != null ? product.getPrice() : 0.0); // Fetch product price
                        dto.setDate(order.getDate());
                        dto.setStatus(order.getStatus());
                        dto.setPayment(order.getPayment());
                        return dto;
                    }).collect(Collectors.toList());

            request.setAttribute("orders", orderDTOList);
            request.setAttribute("mainNav", "AvailableOrders"); // Highlight the active nav
            request.getRequestDispatcher("driverAvailableOrders.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred while loading the orders.");
            request.setAttribute("messageType", "error");
            request.getRequestDispatcher("driverAvailableOrders.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String orderId = request.getParameter("orderId");

        try {
            MyOrder order = myOrderFacade.find(orderId);

            if (order == null) {
                request.setAttribute("message", "Order not found.");
                request.setAttribute("messageType", "error");
            } else if ("deliveredAndPaid".equalsIgnoreCase(action)) {
                order.setPayment("Paid");
                order.setStatus("Delivered");
                myOrderFacade.edit(order);
                request.setAttribute("message", "Order marked as delivered and payment collected.");
                request.setAttribute("messageType", "success");
            } else {
                request.setAttribute("message", "Invalid action.");
                request.setAttribute("messageType", "error");
            }

            doGet(request, response); // Refresh the data to show updated values

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred while updating the order.");
            request.setAttribute("messageType", "error");
            doGet(request, response);
        }
    }
}
