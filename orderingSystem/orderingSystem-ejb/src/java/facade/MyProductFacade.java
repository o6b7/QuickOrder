package facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import model.MyProduct;

@Stateless
public class MyProductFacade extends AbstractFacade<MyProduct> {

    @PersistenceContext(unitName = "orderingSystem-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MyProductFacade() {
        super(MyProduct.class);
    }

    
    /**
     * Searches for products by ID or Name.
     * @param searchQuery The search query (either ID or part of the name).
     * @return List of matching products.
     */
    public List<MyProduct> searchByIdNameOrCategory(String searchTerm) {
        TypedQuery<MyProduct> query = em.createQuery(
                "SELECT p FROM MyProduct p WHERE p.id LIKE :searchTerm OR "
                        + "p.name LIKE :searchTerm OR p.category LIKE :searchTerm",
                MyProduct.class);
        query.setParameter("searchTerm", "%" + searchTerm + "%");
        return query.getResultList();
    }

    public long countTotalProducts() {
        return em.createQuery("SELECT COUNT(p) FROM MyProduct p", Long.class)
                 .getSingleResult();
    }
    


}
