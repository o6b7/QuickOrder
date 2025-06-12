package controller;

import dto.OrderDTO;
import facade.MyOrderFacade;
import facade.MyProductFacade;
import facade.MyStaffFacade;
import model.MyOrder;
import model.MyProduct;
import model.MyStaff;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name = "OrderManagementController", urlPatterns = {"/OrderManagementController"})
public class OrderManagementController extends HttpServlet {

    @EJB
    private MyOrderFacade myOrderFacade;
    @EJB
    private MyProductFacade myProductFacade;
    @EJB
    private MyStaffFacade myStaffFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchQuery = request.getParameter("searchOrderId");
        String mainNav = request.getParameter("mainNav");

        try {
            System.out.println("Search query: " + searchQuery);  // Log the search query

            List<MyOrder> orders = searchQuery != null && !searchQuery.trim().isEmpty()
                    ? myOrderFacade.findOrdersByQuery(searchQuery)
                    : myOrderFacade.findAll();

            System.out.println("Found orders: " + orders.size());  // Log the number of orders found

            List<OrderDTO> orderDTOList = orders.stream()
                    .map(order -> {
                        MyProduct product = myProductFacade.find(order.getMyProductID());
                        return createOrderDTO(order, product);
                    })
                    .collect(Collectors.toList());

            List<MyStaff> deliveryStaff = myStaffFacade.findByRole("Delivery Staff");
            Map<String, Long> staffOrderCounts = myOrderFacade.getStaffOrderCountMap();
            List<Map<String, Object>> driverSuggestions = deliveryStaff.stream()
                    .map(driver -> {
                        Map<String, Object> driverData = new HashMap<>();
                        driverData.put("id", driver.getId());
                        driverData.put("orderCount", staffOrderCounts.getOrDefault(driver.getId(), 0L));
                        return driverData;
                    })
                    .collect(Collectors.toList());

            request.setAttribute("orders", orderDTOList);
            request.setAttribute("driverSuggestions", driverSuggestions);
            request.setAttribute("mainNav", mainNav);
            request.getRequestDispatcher("orderManagement.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");  // Redirect to error page
        }
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String orderId = request.getParameter("orderId");
        String driverId = request.getParameter("driverId");
        try {
            MyOrder order = myOrderFacade.find(orderId);
            if ("approve".equalsIgnoreCase(action)) {
                if (driverId == null || driverId.trim().isEmpty()) {
                    request.setAttribute("message", "Cannot approve order. Driver ID is required.");
                    request.setAttribute("messageType", "error");
                    reloadOrders(request, response);
                    return;
                }
                MyStaff driver = myStaffFacade.find(driverId);
                if (driver == null || !"Delivery Staff".equalsIgnoreCase(driver.getRole())) {
                    request.setAttribute("message", "Cannot approve order. Driver ID does not exist or is not a Delivery Staff.");
                    request.setAttribute("messageType", "error");
                    reloadOrders(request, response);
                    return;
                }
                order.setMyStaffID(driverId);
                order.setStatus("Out for delivery");
                myOrderFacade.edit(order);
                request.setAttribute("message", "Order approved and assigned to driver: " + driverId);
                request.setAttribute("messageType", "success");
            } else if ("reject".equalsIgnoreCase(action)) {
                order.setStatus("Rejected");
                myOrderFacade.edit(order);
                request.setAttribute("message", "Order has been rejected.");
                request.setAttribute("messageType", "success");
            }

            reloadOrders(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    private void reloadOrders(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<MyOrder> orders = myOrderFacade.findAll();
        List<OrderDTO> orderDTOList = orders.stream()
                .map(o -> createOrderDTO(o, myProductFacade.find(o.getMyProductID())))
                .collect(Collectors.toList());

        request.setAttribute("orders", orderDTOList);
        request.getRequestDispatcher("orderManagement.jsp").forward(request, response);
    }

    private MyStaff findAvailableDeliveryStaff() {
        List<MyStaff> deliveryStaffList = myStaffFacade.findByRole("Delivery Staff");
        if (deliveryStaffList.isEmpty()) {
            return null;
        }

        Map<String, Long> staffOrderCountMap = myOrderFacade.getStaffOrderCountMap();
        for (MyStaff staff : deliveryStaffList) {
            if (staff.getId().startsWith("D00") && !staffOrderCountMap.containsKey(staff.getId())) {
                return staff;
            }
        }

        return deliveryStaffList.stream()
                .min(Comparator.comparingLong(
                        staff -> staffOrderCountMap.getOrDefault(staff.getId(), 0L)
                ))
                .orElse(null);
    }

    private MyStaff findLeastLoadedDeliveryStaff() {
        List<MyStaff> deliveryStaffList = myStaffFacade.findByRole("Delivery Staff");
        if (deliveryStaffList.isEmpty()) {
            return null;
        }
        Map<MyStaff, Integer> staffLoadMap = deliveryStaffList.stream()
                .collect(Collectors.toMap(
                        staff -> staff,
                        staff -> myOrderFacade.countOrdersByStaffId(staff.getId())
                ));
        return staffLoadMap.entrySet().stream()
                .min(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);
    }
    
    private OrderDTO createOrderDTO(MyOrder order, MyProduct product) {
        System.out.println("Creating OrderDTO for Order ID: " + order.getMyOrderId()); // Log the order ID

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setMyOrderId(order.getMyOrderId());
        orderDTO.setMyCustomerID(order.getMyCustomerID());
        orderDTO.setMyProductID(order.getMyProductID());
        orderDTO.setProductName(product.getName());
        orderDTO.setProductPrice(product.getPrice());
        orderDTO.setDate(order.getDate());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setMyStaffID(order.getMyStaffID());

        if (product.getPicture() != null) {
            orderDTO.setProductImage(Base64.getEncoder().encodeToString(product.getPicture()));
        } else {
            orderDTO.setProductImage("");
        }

        return orderDTO;
    }

}
