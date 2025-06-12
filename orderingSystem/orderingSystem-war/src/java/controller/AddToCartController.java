package controller;

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
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "AddToCartController", urlPatterns = {"/AddToCartController"})
public class AddToCartController extends HttpServlet {
    @EJB
    private MyOrderFacade myOrderFacade;
    @EJB
    private MyProductFacade myProductFacade;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String customerId = (String) session.getAttribute("userId");
        String productId = request.getParameter("productId");
        String orderId = fetchNextOrderId();

        try {
            MyOrder order = new MyOrder();
            order.setMyOrderId(orderId);
            order.setMyCustomerID(customerId);
            order.setMyStaffID("D0000");
            order.setMyProductID(productId);
            order.setDate(LocalDate.now());
            order.setStatus("Not Assigned");
            myOrderFacade.create(order);

            // Fetch updated product list
            List<MyProduct> productList = myProductFacade.findAll();
            List<MyProduct> productListWithBase64 = productList.stream()
                    .map(this::convertToBase64Image)
                    .collect(Collectors.toList());

            // Set attributes and forward to the JSP
            request.setAttribute("productList", productListWithBase64);
            request.getRequestDispatcher("customerProductsPage.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error adding to cart: " + e.getMessage());
            request.getRequestDispatcher("customerProductsPage.jsp").forward(request, response);
        }
    }

    private String fetchNextOrderId() {
        try {
            String query = "SELECT MAX(o.myOrderId) FROM MyOrder o";
            String lastId = (String) myOrderFacade.getEntityManager()
                    .createQuery(query)
                    .getSingleResult();

            if (lastId != null) {
                int numericPart = Integer.parseInt(lastId.substring(1));
                return String.format("O%03d", numericPart + 1);
            } else {
                return "O001";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "O001";
        }
    }

    private MyProduct convertToBase64Image(MyProduct product) {
        if (product.getPicture() != null) {
            String base64Image = Base64.getEncoder().encodeToString(product.getPicture());
            product.setBase64Image(base64Image);
        }
        return product;
    }
}
