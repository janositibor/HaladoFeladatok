package Week03.movies;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.util.List;

public class RatingRepository {
    private EntityManagerFactory entityManagerFactory;

    public RatingRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<Rating> findRatingByUserName(String username){
        EntityManager em=entityManagerFactory.createEntityManager();
        try {
            List<Rating> result  = em.createQuery("select r from Rating r where user_name=:username", Rating.class)
                    .setParameter("username", username)
                    .getResultList();
            em.close();
            return result;
        }
        catch(NoResultException nre){
            em.close();
            return null;
        }

    }
}
