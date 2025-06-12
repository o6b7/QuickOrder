/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import model.MyStaff;

/**
 *
 * @author qusaimansoor
 */
@Stateless
public class MyStaffFacade extends AbstractFacade<MyStaff> {

    @PersistenceContext(unitName = "orderingSystem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MyStaffFacade() {
        super(MyStaff.class);
    }
    
    public EntityManager getEntityManagerForController() {
        return em;
    }
    
    
    public List<MyStaff> findBySearchTerm(String searchTerm) {
        try {
            // SQL query to search by ID, email, or name
            String query = "SELECT s FROM MyStaff s WHERE " +
                           "LOWER(s.id) LIKE :search " +
                           "OR LOWER(s.email) LIKE :search " +
                           "OR LOWER(s.name) LIKE :search";

            TypedQuery<MyStaff> typedQuery = getEntityManager().createQuery(query, MyStaff.class);
            typedQuery.setParameter("search", "%" + searchTerm.toLowerCase() + "%");
            return typedQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null or an empty list in case of error
        }
    }

    public MyStaff findStaffByEmailAndPassword(String email, String password) {
        try {
            String query = "SELECT s FROM MyStaff s WHERE s.email = :email AND s.password = :password";
            return em.createQuery(query, MyStaff.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null if no result or an error occurs
        }
    }
    
    public MyStaff findLeastLoadedDriver() {
        try {
            String query = "SELECT s FROM MyStaff s WHERE s.role = 'Delivery Staff' ORDER BY " +
                           "(SELECT COUNT(o) FROM MyOrder o WHERE o.myStaffID = s.id) ASC";
            List<MyStaff> drivers = em.createQuery(query, MyStaff.class).setMaxResults(1).getResultList();
            return drivers.isEmpty() ? null : drivers.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<MyStaff> findByRole(String role) {
        return em.createQuery("SELECT s FROM MyStaff s WHERE LOWER(s.role) = :role", MyStaff.class)
                .setParameter("role", role.toLowerCase())
                .getResultList();
    }

    public long countDeliveryStaff() {
        return em.createQuery("SELECT COUNT(s) FROM MyStaff s "
                + "WHERE s.role = 'Delivery Staff'", Long.class)
                 .getSingleResult();
    }

    public long countManagingStaff() {
        return em.createQuery("SELECT COUNT(s) FROM MyStaff s WHERE"
                + " s.role = 'Managing Staff'", Long.class)
                 .getSingleResult();
    }


}
