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
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "CustomerProductsPageController", urlPatterns = {"/CustomerProductsPageController"})
public class CustomerProductsPageController extends HttpServlet {

    @EJB
    private MyProductFacade myProductFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String search = request.getParameter("search");

        try {
            List<MyProduct> productList;
            if (search != null && !search.trim().isEmpty()) {
                productList = myProductFacade.searchByIdNameOrCategory(search.trim());
            } else {
                productList = myProductFacade.findAll();
            }
            List<MyProduct> productListWithBase64 = productList.stream()
                    .map(this::convertToBase64Image)
                    .collect(Collectors.toList());
            request.setAttribute("productList", productListWithBase64);
            request.getRequestDispatcher("customerProductsPage.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error retrieving products: " + e.getMessage());
            request.getRequestDispatcher("customerProductsPage.jsp").forward(request, response);
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
