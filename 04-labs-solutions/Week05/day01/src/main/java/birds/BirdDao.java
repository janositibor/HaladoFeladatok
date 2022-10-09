package birds;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class BirdDao {
    private EntityManagerFactory entityManagerFactory;

    public BirdDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Bird saveBird(Bird birdToInsert){
        EntityManager em=entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(birdToInsert);
            em.getTransaction().commit();
            return birdToInsert;
        } finally {
            em.close();
        }
    }

    public List<Bird> listBirds(){
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            List<Bird> results = em.createQuery("select b from Bird b order by b.id", Bird.class).getResultList();
            return results;
        } finally {
            em.close();
        }
    }

    public List<Bird> listBirdsSpeciesGiven(BirdSpecies species){
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            List<Bird> results = em.createQuery("select b from Bird b WHERE b.birdSpecies=:species order by b.id", Bird.class)
                    .setParameter("species",species)
                    .getResultList();
            return results;
        } finally {
            em.close();
        }
    }

    public List<Bird> listBirdsWithEggsGiven(int eggs){
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            List<Bird> results = em.createQuery("select b from Bird b WHERE b.nest.numberOfEggs=:eggs order by b.id", Bird.class)
                    .setParameter("eggs",eggs)
                    .getResultList();
            return results;
        } finally {
            em.close();
        }
    }

    public void deleteBird(long id){
        EntityManager em=entityManagerFactory.createEntityManager();
        try{
            em.getTransaction().begin();
            Bird entityToDelete=em.getReference(Bird.class,id);
            em.remove(entityToDelete);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
