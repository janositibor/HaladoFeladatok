package birds;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class NestDao {
    private EntityManagerFactory entityManagerFactory;

    public NestDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Nest saveNest(Nest nestToInsert){
        EntityManager em=entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(nestToInsert);
            em.getTransaction().commit();
            return nestToInsert;
        } finally {
            em.close();
        }
    }

    public Nest findNestById(long id){
        EntityManager em=entityManagerFactory.createEntityManager();
        try {
            Nest result=em.find(Nest.class, id);
            return result;
        } finally {
            em.close();
        }
    }

    public Nest findNestWithMinBirds(){
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            Nest results = em.createQuery("select n from Nest n join fetch n.birds WHERE n.birds.size=(SELECT MIN(ne.birds.size) FROM Nest ne)", Nest.class)
                    .getSingleResult();
            return results;
        } finally {
            em.close();
        }
    }

    public long countNestsWithEggsGiven(int eggs){
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            Long results = em.createQuery("select COUNT(n) from Nest n WHERE n.numberOfEggs=:eggs", Long.class)
                    .setParameter("eggs",eggs)
                    .getSingleResult();
            return results;
        } finally {
            em.close();
        }
    }
}
