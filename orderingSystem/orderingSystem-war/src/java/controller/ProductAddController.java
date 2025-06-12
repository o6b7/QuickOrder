package controller;

import facade.MyProductFacade;
import model.MyProduct;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Handles adding new products.
 */
@WebServlet(name = "AddProductController", urlPatterns = {"/AddProductController"})
@MultipartConfig(maxFileSize = 1024 * 1024 * 2)
public class ProductAddController extends HttpServlet {
    @EJB
    private MyProductFacade myProductFacade;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String toggleCategory = request.getParameter("toggleCategory");
        if (toggleCategory != null) {
            // Toggle between adding a new category or using an existing one
            response.sendRedirect("AddProductController?addNewCategory=" + toggleCategory);
            return;
        }
        try {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            String category = request.getParameter("category");
            String newCategory = request.getParameter("newCategory");
            Part picturePart = request.getPart("picture");

            if (description != null && description.length() > 255) {
                description = description.substring(0, 255);
                request.setAttribute("errorMessage", "Description cannot exceed 255 characters. Only the first 255 characters will be saved.");
            }
            String finalCategory = (newCategory != null && !newCategory.trim().isEmpty()) ? newCategory : category;

            String id = generateNextProductId();

            byte[] picture = null;
            if (picturePart != null && picturePart.getSize() > 0) {
                try (InputStream inputStream = picturePart.getInputStream()) {
                    picture = readInputStream(inputStream);
                }
            }

            MyProduct product = new MyProduct();
            product.setId(id);
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setStock(stock);
            product.setCategory(finalCategory);
            product.setPicture(picture);
            product.setPublishDate(LocalDate.now()); // Set publish date to today

            myProductFacade.create(product);

            response.sendRedirect("ProductManagementController?successMessage=Product+added+successfully");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error adding product: " + e.getMessage());
            request.getRequestDispatcher("addProduct.jsp").forward(request, response);
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

    private String generateNextProductId() {
        try {
            List<MyProduct> products = myProductFacade.findAll();
            int maxId = products.stream()
                    .mapToInt(product -> Integer.parseInt(product.getId().replace("P", "")))
                    .max()
                    .orElse(0);
            return "P" + String.format("%03d", maxId + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return "P001";
        }
    }
}
