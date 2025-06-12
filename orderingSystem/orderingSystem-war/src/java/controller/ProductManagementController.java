package controller;

import facade.MyProductFacade;
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
import java.util.Base64;

/**
 * Handles product management, including search and edit functionality.
 */
@WebServlet(name = "ProductManagementController", urlPatterns = {"/ProductManagementController"})
public class ProductManagementController extends HttpServlet {

    @EJB
    private MyProductFacade myProductFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String search = request.getParameter("search");

        try {
            if ("edit".equalsIgnoreCase(action)) {
                // Edit Product
                String id = request.getParameter("id");
                MyProduct product = myProductFacade.find(id);
                request.setAttribute("product", convertToBase64Image(product));
                request.getRequestDispatcher("productEdit.jsp").forward(request, response);
            } else {
                List<MyProduct> productList;
                if (search != null && !search.trim().isEmpty()) {
                    // Perform search by name, id, or category
                    productList = myProductFacade.searchByIdNameOrCategory(search.trim());
                } else {
                    productList = myProductFacade.findAll();
                }
                List<MyProduct> productListWithBase64 = productList.stream()
                        .map(this::convertToBase64Image)
                        .collect(Collectors.toList());
                request.setAttribute("productList", productListWithBase64);
                request.getRequestDispatcher("productManagement.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("productManagement.jsp").forward(request, response);
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
