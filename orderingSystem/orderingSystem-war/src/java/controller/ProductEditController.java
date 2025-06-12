package controller;

import facade.MyProductFacade;
import model.MyProduct;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Handles editing product information.
 */
@WebServlet(name = "ProductEditController", urlPatterns = {"/ProductEditController"})
@MultipartConfig(maxFileSize = 1024 * 1024 * 2)

public class ProductEditController extends HttpServlet {
    @EJB
    private MyProductFacade myProductFacade;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String id = request.getParameter("id");
            MyProduct product = myProductFacade.find(id);
            if (product == null) {
                throw new Exception("Product not found.");
            }
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            String category = request.getParameter("category");
            Part picturePart = request.getPart("picture");
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setStock(stock);
            product.setCategory(category);
            if (picturePart != null && picturePart.getSize() > 0) {
                try (InputStream inputStream = picturePart.getInputStream()) {
                    product.setPicture(readInputStream(inputStream));
                }
            }
            myProductFacade.edit(product);
            response.sendRedirect("ProductManagementController?successMessage=Product+updated+successfully");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error updating product: " + e.getMessage());
            request.getRequestDispatcher("productEdit.jsp").forward(request, response);
        }
    }
    private byte[] readInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, bytesRead);
        }
        return buffer.toByteArray();
    }

}
