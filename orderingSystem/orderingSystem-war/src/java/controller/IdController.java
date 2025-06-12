package controller;


import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import facade.MyCustomerFacade;

@WebServlet(name = "IdController", urlPatterns = {"/IdController"})
public class IdController extends HttpServlet {

    @EJB
    private MyCustomerFacade myCustomerFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Generate the next customer ID
            String nextId = generateNextCustomerId();

            // Return the generated ID
            response.setContentType("text/plain");
            response.getWriter().write(nextId);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating ID: " + e.getMessage());
        }
    }

    private String generateNextCustomerId() {
        try {
            // Query to get the highest customer ID using the public EntityManager accessor
            String lastId = (String) myCustomerFacade.getEntityManagerForController()
                .createQuery("SELECT MAX(c.id) FROM MyCustomer c")
                .getSingleResult();

            if (lastId != null) {
                // Remove the "C00" prefix and parse the numeric part
                int numericPart = Integer.parseInt(lastId.substring(3));
                return String.format("C00%d", numericPart + 1); // Example: C0023 -> C0024
            } else {
                // If the table is empty, start with "C001"
                return "C001";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "C001"; // Fallback for errors
        }
    }
}
