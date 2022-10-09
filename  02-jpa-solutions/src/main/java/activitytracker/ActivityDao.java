package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.List;

public class ActivityDao {
    private EntityManagerFactory entityManagerFactory;

    public ActivityDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    List<Object[]> findTrackPointCountByActivity(){
        EntityManager em=entityManagerFactory.createEntityManager();
        try{
            List<Object[]> result = em.createQuery("SELECT description, SIZE(a.trackPoints) FROM Activity a ORDER BY a.description",
                            Object[].class)
                    .getResultList();
            return result;
        } finally {
            em.close();
        }
    }

    List<Coordinate> findTrackPointCoordinatesByDate(LocalDateTime afterThis, int start, int max){
        EntityManager em=entityManagerFactory.createEntityManager();
        try{
            List<Coordinate> coordinates = em
                    .createNamedQuery("findTrackPointCoordinatesByDate")
                    .setParameter("time", afterThis.toLocalDate())
                    .setFirstResult(start)
                    .setMaxResults(max)
                    .getResultList();
            return coordinates;
        } finally {
            em.close();
        }

    }


    public void mergeActivity(Activity activity){
        EntityManager em=entityManagerFactory.createEntityManager();
        try{
            em.getTransaction().begin();
            Activity mergedActivity=em.merge(activity);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void saveActivity(Activity activityToInsert){
        EntityManager em=entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(activityToInsert);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Activity findById(Long id){
        EntityManager em=entityManagerFactory.createEntityManager();
        try{
            Activity activity=em.find(Activity.class,id);
            return activity;
        } finally {
            em.close();
        }
    }

    public  Activity findActivityByIdWithAreas(long id){
        EntityManager em=entityManagerFactory.createEntityManager();
        try{
            Activity activity=em.createQuery("select a from Activity a Left join fetch a.areas where a.id=:id", Activity.class)
                    .setParameter("id",id)
                    .getSingleResult();
//        Activity activity=em.createQuery("select a from Activity a order by a.i", Activity.class).getSingleResult();
            return activity;
        } finally {
            em.close();
        }

    }

    public  Activity findActivityByIdWithTrackPoints(long id){
        EntityManager em=entityManagerFactory.createEntityManager();
        try{
            Activity activity=em.createQuery("select a from Activity a Left join fetch a.trackPoints where a.id=:id", Activity.class)
                    .setParameter("id",id)
                    .getSingleResult();
//        Activity activity=em.createQuery("select a from Activity a order by a.i", Activity.class).getSingleResult();
            return activity;
        } finally {
            em.close();
        }
    }

    public Activity findActivityByIdWithLabels(long id){
        EntityManager em=entityManagerFactory.createEntityManager();
        try{
            Activity activity=em.createQuery("select a from Activity a join fetch a.labels where id=:id", Activity.class)
                .setParameter("id",id)
                .getSingleResult();
            return activity;
        } finally {
            em.close();
        }

    }
    public List<Activity> listActivities() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            List<Activity> activities = em.createQuery("select a from Activity a order by a.id", Activity.class).getResultList();
            return activities;
        } finally {
            em.close();
        }
    }

    public void changeDescription(Long id, String description){
        EntityManager em=entityManagerFactory.createEntityManager();
        try{
            em.getTransaction().begin();
            Activity activity=em.find(Activity.class,id);
            activity.setDescription(description);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    public void setLabels(Long id, List<String> labels){
        EntityManager em=entityManagerFactory.createEntityManager();
        try{
            em.getTransaction().begin();
            Activity activity=em.find(Activity.class,id);
            activity.setLabels(labels);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void delete(Long id){
        EntityManager em=entityManagerFactory.createEntityManager();
        try{
            em.getTransaction().begin();
            Activity activityToDelete=em.getReference(Activity.class,id);
            em.remove(activityToDelete);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
