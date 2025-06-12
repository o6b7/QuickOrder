package controller;

import dto.OrderDTO;
import facade.MyFeedbackFacade;
import facade.MyOrderFacade;
import facade.MyProductFacade;
import model.MyOrder;
import model.MyProduct;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Base64;
import java.util.Map;

@WebServlet(name = "CustomerOrdersController", urlPatterns = {"/CustomerOrdersController"})
public class CustomerOrdersController extends HttpServlet {

    @EJB
    private MyOrderFacade myOrderFacade;

    @EJB
    private MyProductFacade myProductFacade;

    @EJB
    private MyFeedbackFacade myFeedbackFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String customerId = (String) session.getAttribute("userId");
        String searchOrderId = request.getParameter("searchOrderId");
        try {
            List<MyOrder> allOrders;
            if (searchOrderId != null && !searchOrderId.isEmpty()) {
                MyOrder order = myOrderFacade.findOrderByIdAndCustomerId(searchOrderId, customerId);
                if (order != null) {
                    MyProduct product = myProductFacade.find(order.getMyProductID());
                    OrderDTO orderDTO = createOrderDTO(order, product);
                    Map<String, Object> feedback = myFeedbackFacade.findDriverFeedback(
                        order.getMyStaffID(), order.getMyProductID(), customerId);

                    if (feedback != null) {
                        orderDTO.setFeedback(feedback);
                    }
                    if ("Not Assigned".equals(order.getStatus())) {
                        request.setAttribute("pendingOrders", Collections.singletonList(orderDTO));
                    } else if ("Assigned".equals(order.getStatus())) {
                        request.setAttribute("outForDeliveryOrders", Collections.singletonList(orderDTO));
                    } else if ("Delivered".equals(order.getStatus())) {
                        request.setAttribute("deliveredOrders", Collections.singletonList(orderDTO));
                    }
                } else {
                    request.setAttribute("searchMessage", "No orders found for the given Order ID.");
                }
            } else {
                allOrders = myOrderFacade.findOrdersByCustomerId(customerId);
                List<OrderDTO> pendingOrders = new ArrayList<>();
                List<OrderDTO> outForDeliveryOrders = new ArrayList<>();
                List<OrderDTO> deliveredOrders = new ArrayList<>();
                for (MyOrder order : allOrders) {
                    MyProduct product = myProductFacade.find(order.getMyProductID());
                    OrderDTO orderDTO = createOrderDTO(order, product);
                    Map<String, Object> feedback = myFeedbackFacade.findDriverFeedback(
                        order.getMyStaffID(), order.getMyProductID(), customerId);
                    if (feedback != null) {
                        orderDTO.setFeedback(feedback);
                    }
                    switch (order.getStatus()) {
                        case "Not Assigned":
                            pendingOrders.add(orderDTO);
                            break;
                        case "Out for delivery":
                            outForDeliveryOrders.add(orderDTO);
                            break;
                        case "Delivered":
                            deliveredOrders.add(orderDTO);
                            break;
                    }
                }
                request.setAttribute("pendingOrders", pendingOrders);
                request.setAttribute("outForDeliveryOrders", outForDeliveryOrders);
                request.setAttribute("deliveredOrders", deliveredOrders);
            }
            request.getRequestDispatcher("customerOrdersPage.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("customerOrdersPage.jsp?error=ErrorFetchingOrders");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("cancelOrder".equals(action)) {
            String orderId = request.getParameter("orderId");

            try {
                MyOrder order = myOrderFacade.find(orderId);
                if (order != null) {
                    myOrderFacade.remove(order); // Remove the order from the database
                    response.sendRedirect("CustomerOrdersController?message=Order+Cancelled+Successfully");
                } else {
                    response.sendRedirect("CustomerOrdersController?message=Order+Not+Found");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("CustomerOrdersController?message=Error+Cancelling+Order");
            }
        } else {
            response.sendRedirect("CustomerOrdersController");
        }
    }

    private OrderDTO createOrderDTO(MyOrder order, MyProduct product) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setMyOrderId(order.getMyOrderId());
        orderDTO.setMyProductID(order.getMyProductID());
        orderDTO.setProductName(product.getName());
        orderDTO.setProductDescription(product.getDescription());
        orderDTO.setProductPrice(product.getPrice());
        orderDTO.setMyStaffID(order.getMyStaffID()); // Set myStaffID
        orderDTO.setDate(order.getDate());
        orderDTO.setStatus(order.getStatus());
        if (product.getPicture() != null) {
            orderDTO.setProductImage(Base64.getEncoder().encodeToString(product.getPicture()));
        } else {
            orderDTO.setProductImage(""); // Set empty string if no image
        }
        return orderDTO;
    }
}
