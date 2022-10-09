package Week03.movies;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.util.Optional;

public class MovieRepository {

    private EntityManagerFactory entityManagerFactory;

    public MovieRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Movie saveMovie(Movie movieToInsert){
        EntityManager em=entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(movieToInsert);
        em.getTransaction().commit();
        em.close();
        return movieToInsert;
    }

    public Optional<Movie> findByTitle(String title){
        EntityManager em=entityManagerFactory.createEntityManager();

        try {
            Movie movie = em.createQuery("select m from Movie m where title=:title", Movie.class)
                    .setParameter("title", title)
                    .getSingleResult();
            em.close();
            return Optional.of(movie);
        }
        catch(NoResultException nre){
            em.close();
            return Optional.empty();
        }
     }
    public Optional<Movie> findByTitleWithRatings(String title){
        EntityManager em=entityManagerFactory.createEntityManager();

        try {
            Movie movie=em.createQuery("select m from Movie m join fetch m.ratings where title=:title", Movie.class)
                    .setParameter("title", title)
                    .getSingleResult();
            em.close();
            return Optional.of(movie);
        }
        catch(NoResultException nre){
            em.close();
            return Optional.empty();
        }
    }
}
