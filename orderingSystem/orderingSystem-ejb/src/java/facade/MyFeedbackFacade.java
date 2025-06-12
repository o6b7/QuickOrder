package facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import model.MyFeedback;

/**
 * Facade for managing feedback data related to products and drivers.
 */
@Stateless
public class MyFeedbackFacade extends AbstractFacade<MyFeedback> {

    @PersistenceContext(unitName = "orderingSystem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MyFeedbackFacade() {
        super(MyFeedback.class);
    }
    
    /**
     * Generates the next feedback ID based on the last feedback ID in the database.
     * 
     * @return The next feedback ID.
     */
    public String generateNextFeedbackId() {
        synchronized (this) {
            try {
                String lastId = (String) em.createQuery("SELECT MAX(f.myFeedbackID) FROM MyFeedback f")
                                           .getSingleResult();

                if (lastId != null) {
                    int numericPart = Integer.parseInt(lastId.substring(1)); // Adjusted for "F###" format
                    return String.format("F%03d", numericPart + 1);
                } else {
                    return "F001"; // Default start ID
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "F001"; // Fallback ID
            }
        }
    }


    
    /**
     * Retrieves the last feedback ID.
     * 
     * @return The last feedback ID.
     */
    public String getLastFeedbackId() {
        try {
            return em.createQuery("SELECT MAX(f.myFeedbackID) FROM MyFeedback f", String.class)
                     .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null if no feedback exists
        }
    }
    
    /**
     * Retrieves feedback for a specific product by its ID.
     * 
     * @param productId The product ID.
     * @return A list of feedback for the product.
     */
    public List<Object[]> findProductFeedback(String productId) {
        return em.createQuery(
                "SELECT c.name, f.content, f.rating, f.date " +
                "FROM MyFeedback f JOIN MyCustomer c ON f.myCustomerID = c.id " +
                "WHERE f.myProductID = :productId AND f.type = 'product'", Object[].class)
                .setParameter("productId", productId)
                .getResultList();
    }

    /**
     * Retrieves feedback for a specific driver, identified by staff ID, product ID, and customer ID.
     * 
     * @param myStaffID The staff ID (driver).
     * @param myProductID The product ID.
     * @param myCustomerID The customer ID.
     * @return A map containing feedback details.
     */
    public Map<String, Object> findDriverFeedback(String myStaffID, String myProductID, String myCustomerID) {
        try {
            // Fetch the feedback as a List of Object arrays
            List<Object[]> results = em.createQuery(
                    "SELECT f.content, f.rating, f.myFeedbackID " +
                    "FROM MyFeedback f " +
                    "WHERE f.myStaffID = :myStaffID AND f.myProductID = :myProductID " +
                    "AND f.myCustomerID = :myCustomerID AND f.type = 'driver'",
                    Object[].class)
                    .setParameter("myStaffID", myStaffID)
                    .setParameter("myProductID", myProductID)
                    .setParameter("myCustomerID", myCustomerID)
                    .getResultList();

            // Check if there are any results
            if (!results.isEmpty()) {
                Object[] result = results.get(0); // Get the first result
                Map<String, Object> feedback = new HashMap<>();
                feedback.put("content", result[0]);
                feedback.put("rating", result[1]);
                feedback.put("feedbackId", result[2]);
                return feedback; // Return the map
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Return null if no feedback is found or an error occurs
    }


    /**
     * Retrieves feedback for a specific driver, identified by staff ID.
     * 
     * @param myStaffID The staff ID (driver).
     * @return A list of feedback for the driver.
     */
    public List<Object[]> findDriverFeedback(String myStaffID) {
        return em.createQuery(
                "SELECT f.myCustomerID, f.content, f.rating, f.date " +
                "FROM MyFeedback f WHERE f.myStaffID = :myStaffID AND f.type = 'driver'",
                Object[].class)
                .setParameter("myStaffID", myStaffID)
                .getResultList();
    }

    /**
     * Counts the number of feedback entries for drivers.
     * 
     * @return The count of feedback for drivers.
     */
    public long countDriverFeedbacks() {
        return em.createQuery("SELECT COUNT(f) FROM MyFeedback f WHERE f.type = 'driver'", Long.class)
                 .getSingleResult();
    }

    /**
     * Counts the number of feedback entries for products.
     * 
     * @return The count of feedback for products.
     */
    public long countProductFeedbacks() {
        return em.createQuery("SELECT COUNT(f) FROM MyFeedback f WHERE f.type = 'product'", Long.class)
                 .getSingleResult();
    }

    /**
     * Retrieves all feedback entries.
     * 
     * @return A list of all feedback entries.
     */
    public List<Object[]> findAllFeedback() {
        return em.createQuery(
                "SELECT f.myFeedbackID, f.rating, f.content, f.date, f.type, f.myProductID, f.myStaffID " +
                "FROM MyFeedback f", Object[].class)
                .getResultList();
    }

    /**
     * Searches for feedback based on a search query.
     * 
     * @param searchQuery The search query.
     * @return A list of feedback that matches the search query.
     */
    public List<MyFeedback> searchFeedback(String searchQuery) {
        return em.createQuery(
                "SELECT f FROM MyFeedback f WHERE " +
                "LOWER(f.myProductID) LIKE :query OR " +
                "LOWER(f.myStaffID) LIKE :query OR " +
                "LOWER(f.myCustomerID) LIKE :query", MyFeedback.class)
                .setParameter("query", "%" + searchQuery.toLowerCase() + "%")
                .getResultList();
    }
   



}
