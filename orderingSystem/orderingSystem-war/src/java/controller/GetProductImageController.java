package controller;

import facade.MyProductFacade;
import model.MyProduct;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "GetProductImageController", urlPatterns = {"/GetProductImageController"})
public class GetProductImageController extends HttpServlet {

    @EJB
    private MyProductFacade myProductFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String id = request.getParameter("id");
            MyProduct product = myProductFacade.find(id);

            if (product != null && product.getPicture() != null) {
                response.setContentType("image/jpeg");
                response.getOutputStream().write(product.getPicture());
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
