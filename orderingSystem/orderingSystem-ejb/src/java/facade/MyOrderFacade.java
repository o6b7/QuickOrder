/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import model.MyOrder;

/**
 *
 * @author qusaimansoor
 */
@Stateless
public class MyOrderFacade extends AbstractFacade<MyOrder> {

    @PersistenceContext(unitName = "orderingSystem-ejbPU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }


    public MyOrderFacade() {
        super(MyOrder.class);
    }

    
    public List<MyOrder> findOrdersByCustomerId(String customerId) {
        try {
            return em.createQuery("SELECT o FROM MyOrder o WHERE o.myCustomerID = :customerId", MyOrder.class)
                     .setParameter("customerId", customerId)
                     .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list in case of an error
        }
    }


    public List<MyOrder> searchByProductOrStatus(String customerId, String searchTerm) {
        System.out.println("Executing searchByProductOrStatus for customer: " + customerId + " with search: " + searchTerm);
        String queryStr = "SELECT o FROM MyOrder o WHERE o.myCustomerID = :customerId AND "
                        + "(LOWER(o.myProductID) LIKE :search OR LOWER(o.status) LIKE :search)";
        TypedQuery<MyOrder> query = em.createQuery(queryStr, MyOrder.class);
        query.setParameter("customerId", customerId);
        query.setParameter("search", "%" + searchTerm.toLowerCase() + "%");
        return query.getResultList();
    }
    
    public MyOrder findOrderByIdAndCustomerId(String orderId, String customerId) {
        try {
            return em.createQuery("SELECT o FROM MyOrder o WHERE o.myOrderId = :orderId AND o.myCustomerID = :customerId", MyOrder.class)
                    .setParameter("orderId", orderId)
                    .setParameter("customerId", customerId)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null if not found
        }
    }

    
    public List<MyOrder> searchByOrderCustomerProduct(String search) {
        String query = "SELECT o FROM MyOrder o WHERE LOWER(o.myOrderId) LIKE :search " +
                       "OR LOWER(o.myCustomerID) LIKE :search " +
                       "OR LOWER(o.myProductID) LIKE :search";
        return em.createQuery(query, MyOrder.class)
                 .setParameter("search", "%" + search.toLowerCase() + "%")
                 .getResultList();
    }

    public int countOrdersByStaffId(String staffId) {
        try {
            return em.createQuery("SELECT COUNT(o) FROM MyOrder o WHERE o.myStaffID = :staffId", Long.class)
                    .setParameter("staffId", staffId)
                    .getSingleResult()
                    .intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Return 0 if an error occurs
        }
    }
    
        public Map<String, Long> getStaffOrderCountMap() {
        List<Object[]> result = em.createQuery(
                "SELECT o.myStaffID, COUNT(o) FROM MyOrder o WHERE o.myStaffID IS NOT NULL GROUP BY o.myStaffID",
                Object[].class
        ).getResultList();

        return result.stream().collect(Collectors.toMap(
                row -> (String) row[0], // Staff ID
                row -> (Long) row[1]    // Count of orders
        ));
    }

    public List<MyOrder> findOrdersByQuery(String query) {
        try {
            System.out.println("Executing query: " + query); // Log the search term
            String jpql = "SELECT o FROM MyOrder o WHERE " +
                          "LOWER(o.myOrderId) LIKE :query OR " +
                          "LOWER(o.myStaffID) LIKE :query OR " +
                          "LOWER(o.myCustomerID) LIKE :query";
            return em.createQuery(jpql, MyOrder.class)
                     .setParameter("query", "%" + query.toLowerCase() + "%")
                     .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return empty list in case of error
        }
    }


    public boolean hasCustomerOrderedProduct(String customerId, String productId) {
        Long count = em.createQuery(
                "SELECT COUNT(o) FROM MyOrder o WHERE o.myCustomerID = :customerId "
                        + "AND o.myProductID = :productId", Long.class)
                .setParameter("customerId", customerId)
                .setParameter("productId", productId)
                .getSingleResult();

        return count > 0;
    }


    public long countTotalOrders() {
        return em.createQuery("SELECT COUNT(o) FROM MyOrder o", Long.class)
                 .getSingleResult();
    }

    public void remove(MyOrder order) {
        em.remove(em.merge(order)); // Remove the order from the database
    }

    
}
