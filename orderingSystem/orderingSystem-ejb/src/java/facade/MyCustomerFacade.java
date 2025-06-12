/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import model.MyCustomer;

/**
 *
 * @author qusaimansoor
 */
@Stateless
public class MyCustomerFacade extends AbstractFacade<MyCustomer> {

    @PersistenceContext(unitName = "orderingSystem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public EntityManager getEntityManagerForController() {
        return em;
    }

    public MyCustomerFacade() {
        super(MyCustomer.class);
    }
    
    public List<MyCustomer> findBySearchTerm(String searchTerm) {
        try {
            // SQL query to search by ID, email, or name
            String query = "SELECT c FROM MyCustomer c WHERE " +
                           "LOWER(c.id) LIKE :search OR " +
                           "LOWER(c.email) LIKE :search OR " +
                           "LOWER(c.name) LIKE :search";

            TypedQuery<MyCustomer> typedQuery = getEntityManager().createQuery(query, MyCustomer.class);
            typedQuery.setParameter("search", "%" + searchTerm.toLowerCase() + "%"); // Case-insensitive search
            return typedQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList(); // Return an empty list in case of error
        }
    }


    public MyCustomer findCustomerByEmailAndPassword(String email, String password) {
        try {
            return em.createQuery("SELECT c FROM MyCustomer c WHERE c.email = :email AND"
                    + " c.password = :password", MyCustomer.class)
                     .setParameter("email", email)
                     .setParameter("password", password)
                     .getSingleResult();
        } catch (NoResultException e) {
            // Log the exception for debugging purposes
            System.out.println("No customer found with email: " + email);
            return null; // Return null if no result is found
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Handle other exceptions gracefully
        }
    }

    public long countTotalCustomers() {
        return em.createQuery("SELECT COUNT(c) FROM MyCustomer c", Long.class)
                 .getSingleResult();
    }

    public long countFemaleCustomers() {
        return em.createQuery("SELECT COUNT(c) FROM MyCustomer c WHERE c.gender = 'Female'", Long.class)
                 .getSingleResult();
    }

    public long countMaleCustomers() {
        return em.createQuery("SELECT COUNT(c) FROM MyCustomer c WHERE c.gender = 'Male'", Long.class)
                 .getSingleResult();
    }

    public MyCustomer findByEmail(String email) {
        try {
            return (MyCustomer) em.createQuery("SELECT c FROM MyCustomer c WHERE c.email = :email")
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // Return null if no customer is found
        }
    }

    public MyCustomer find(String userId) {
        return em.find(MyCustomer.class, userId);
    }



    
}
