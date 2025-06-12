/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RateDriverController", urlPatterns = {"/RateDriverController"})
public class RateDriverController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String orderId = request.getParameter("orderId"); // Order ID passed from the JSP

            // Logic to fetch driver details related to the order and pass it to a JSP
            // Example: Fetch driver details using a facade
            // Driver driver = myDriverFacade.findDriverByOrderId(orderId);

            // Set the driver details as a request attribute (placeholder)
            request.setAttribute("driver", "Driver Info Placeholder");
            request.setAttribute("orderId", orderId);

            // Forward to the rate driver JSP
            request.getRequestDispatcher("rateDriver.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("customerOrdersPage.jsp?error=ErrorFetchingDriverDetails");
        }
    }
}
