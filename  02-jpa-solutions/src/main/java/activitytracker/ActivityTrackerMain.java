package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class ActivityTrackerMain {
    public static void main(String[] args) {
        ActivityTrackerMain activityTrackerMain=new ActivityTrackerMain();
        EntityManagerFactory factory= Persistence.createEntityManagerFactory("pu");
        EntityManager em=factory.createEntityManager();
        Activity activityToInsert=new Activity(LocalDateTime.of(2022, 04, 18, 23,33), "Éjszakai futás", ActivityType.RUNNING);
        activityTrackerMain.persist(em,activityToInsert);
        activityToInsert=new Activity(LocalDateTime.of(2022, 04, 22, 14,01), "Falmászás", ActivityType.HIKING);
        Activity activityToCheck=activityTrackerMain.persist(em,activityToInsert);
        activityToInsert=new Activity(LocalDateTime.of(1984, 11, 11, 21,49), "Laza kosarazás", ActivityType.BASKETBALL);
        activityTrackerMain.persist(em,activityToInsert);
        long idToRead=activityToCheck.getId();
        System.out.println(idToRead);
        Activity activityReadFromDB=em.find(Activity.class,idToRead);
        System.out.println(activityReadFromDB);
        System.out.println();

        em.getTransaction().begin();
        activityReadFromDB.setDescription("Éjszakai túra");
        em.getTransaction().commit();
        System.out.println(activityReadFromDB);
        System.out.println();

        em.getTransaction().begin();
        List<Activity> activities=em.createQuery("select e from Activity e",Activity.class).getResultList();
        em.getTransaction().commit();
        System.out.println(activities);
        System.out.println();

        em.getTransaction().begin();
        Activity activityToDelete=em.find(Activity.class,idToRead);
        em.remove(activityToDelete);
        em.getTransaction().commit();

        em.getTransaction().begin();
        activities=em.createQuery("select e from Activity e",Activity.class).getResultList();
        em.getTransaction().commit();
        System.out.println(activities);

        em.close();
        factory.close();
    }
    private Activity persist(EntityManager em, Activity activityToInsert){
        em.getTransaction().begin();
        em.persist(activityToInsert);
        em.getTransaction().commit();
        return activityToInsert;
    }
}
