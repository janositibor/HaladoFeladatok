package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class AreaDao {
    private EntityManagerFactory entityManagerFactory;

    public AreaDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void saveArea(Area areaToInsert){
        EntityManager em=entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(areaToInsert);
        em.getTransaction().commit();
        em.close();
    }

    public Area findByIdWithCities(Long id){
        EntityManager em=entityManagerFactory.createEntityManager();
        Area area=em.createQuery("select a from Area a Left join fetch a.cities where a.id=:id", Area.class)
                .setParameter("id",id)
                .getSingleResult();
        em.close();
        return area;
    }
}
