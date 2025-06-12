/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import facade.MyOrderFacade;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MyOrder;

/**
 *
 * @author qusaimansoor
 */
@WebServlet(name = "CancelOrderController", urlPatterns = {"/CancelOrderController"})
public class CancelOrderController extends HttpServlet {

    @EJB
    private MyOrderFacade myOrderFacade;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orderId = request.getParameter("orderId");

        try {
            MyOrder order = myOrderFacade.find(orderId);
            if (order != null && "Not Assigned".equals(order.getStatus())) {  // Only cancel "Not Assigned" orders
                myOrderFacade.remove(order);  // Remove the order from the table
                response.sendRedirect("CustomerOrdersController?message=Order+Cancelled+Successfully"); // Redirect to fetch updated orders
            } else {
                response.sendRedirect("CustomerOrdersController?message=Order+Not+Found+Or+Cannot+Be+Cancelled");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("CustomerOrdersController?message=Error+Cancelling+Order");
        }
    }
}
