package activitytracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActivityDaoTest {
    private EntityManagerFactory factory;
    private ActivityDao activityDao;

    @BeforeEach
    void init(){
        factory= Persistence.createEntityManagerFactory("pu");
        activityDao=new ActivityDao(factory);
        insertDummy();
    }
	
	@AfterEach
	void close(){
		factory.close();
	}

    void insertDummy(){
        Activity activityToInsert1=new Activity(LocalDateTime.of(2022, 4, 18, 23,33), "Éjszakai futás teszt", ActivityType.RUNNING);
        Activity activityToInsert2=new Activity(LocalDateTime.of(2022, 4, 22, 14,1), "Falmászás teszt", ActivityType.HIKING);
        Activity activityToInsert3=new Activity(LocalDateTime.of(1984, 11, 11, 21,49), "Laza kosarazás teszt", ActivityType.BASKETBALL);

        activityDao.saveActivity(activityToInsert1);
        activityDao.saveActivity(activityToInsert2);
        activityDao.saveActivity(activityToInsert3);
    }

    @Test
    @DisplayName("Get result with CreateQuery Test")
    void createQueryTest(){
        TrackPoint trackpoint1=new TrackPoint(LocalDate.of(2022,4,30),1.0,2.0);
        TrackPoint trackpoint2=new TrackPoint(LocalDate.of(2022,4,1),2.2,3.9);
        TrackPoint trackpoint3=new TrackPoint(LocalDate.of(2022,5,1),21.2,33.9);
        TrackPoint trackpoint4=new TrackPoint(LocalDate.of(2022,4,19),82.4,83.8);
        TrackPoint trackpoint5=new TrackPoint(LocalDate.of(2022,5,1),48.4,56.8);

        Activity activity1=new Activity(LocalDateTime.of(1984, 11, 11, 21,49), "Kék túra", ActivityType.BASKETBALL);
        activity1.addTrackPoint(trackpoint1);
        activity1.addTrackPoint(trackpoint2);
        activity1.addTrackPoint(trackpoint3);
        activity1.addTrackPoint(trackpoint4);
        activity1.addTrackPoint(trackpoint5);
        activityDao.saveActivity(activity1);

        Activity activityReload1=activityDao.findActivityByIdWithTrackPoints(activity1.getId());

        TrackPoint trackpoint11=new TrackPoint(LocalDate.of(2022,4,3),11.0,12.0);
        TrackPoint trackpoint12=new TrackPoint(LocalDate.of(2022,4,21),22.2,23.9);
        TrackPoint trackpoint13=new TrackPoint(LocalDate.of(2022,5,1),41.2,43.9);
        TrackPoint trackpoint14=new TrackPoint(LocalDate.of(2022,4,29),52.4,53.8);

        Activity activity2=new Activity(LocalDateTime.of(1976, 3, 8, 12,49), "Bringa", ActivityType.BIKING);
        activity2.addTrackPoint(trackpoint11);
        activity2.addTrackPoint(trackpoint12);
        activity2.addTrackPoint(trackpoint13);
        activity2.addTrackPoint(trackpoint14);
        activityDao.saveActivity(activity2);

        List<Object[]> result=activityDao.findTrackPointCountByActivity();

        System.out.println(result);

        assertEquals(5, result.size());

        Object[] Data1 = new Object[]{"Bringa", 4};
        Object[] Data2 = new Object[]{"Éjszakai futás teszt", 0};
        Object[] Data4 = new Object[]{"Kék túra", 5};
        Object[] Data5 = new Object[]{"Laza kosarazás teszt", 0};

        assertArrayEquals(Data1, result.get(0));
        assertArrayEquals(Data2, result.get(1));
        assertArrayEquals(Data4, result.get(3));
        assertArrayEquals(Data5, result.get(4));
    }

    @Test
    @DisplayName("Get Coordinates with NamedQuery Test")
    void getTrackPointsWithNamedQueryTest(){
        TrackPoint trackpoint1=new TrackPoint(LocalDate.of(2022,4,30),1.0,2.0);
        TrackPoint trackpoint2=new TrackPoint(LocalDate.of(2022,4,1),2.2,3.9);
        TrackPoint trackpoint3=new TrackPoint(LocalDate.of(2022,5,1),21.2,33.9);
        TrackPoint trackpoint4=new TrackPoint(LocalDate.of(2022,4,19),82.4,83.8);

        Activity activity1=new Activity(LocalDateTime.of(1984, 11, 11, 21,49), "Kék túra", ActivityType.BASKETBALL);
        activity1.addTrackPoint(trackpoint1);
        activity1.addTrackPoint(trackpoint2);
        activity1.addTrackPoint(trackpoint3);
        activity1.addTrackPoint(trackpoint4);
        activityDao.saveActivity(activity1);

        Activity activityReload1=activityDao.findActivityByIdWithTrackPoints(activity1.getId());

        TrackPoint trackpoint11=new TrackPoint(LocalDate.of(2022,4,3),11.0,12.0);
        TrackPoint trackpoint12=new TrackPoint(LocalDate.of(2022,4,21),22.2,23.9);
        TrackPoint trackpoint13=new TrackPoint(LocalDate.of(2022,5,1),41.2,43.9);
        TrackPoint trackpoint14=new TrackPoint(LocalDate.of(2022,4,29),52.4,53.8);

        Activity activity2=new Activity(LocalDateTime.of(1976, 3, 8, 12,49), "Bringa", ActivityType.BIKING);
        activity2.addTrackPoint(trackpoint11);
        activity2.addTrackPoint(trackpoint12);
        activity2.addTrackPoint(trackpoint13);
        activity2.addTrackPoint(trackpoint14);
        activityDao.saveActivity(activity2);

        System.out.println(activityDao.findTrackPointCoordinatesByDate(LocalDateTime.of(2022,4,18,11,49), 0, 10));

        assertEquals(List.of(83.8, 23.9),activityDao.findTrackPointCoordinatesByDate(LocalDateTime.of(2022,4,18,11,49), 0, 2).stream()
                .map(Coordinate::getLongitude)
                .toList());
        assertEquals(List.of(52.4, 1.0, 21.2),activityDao.findTrackPointCoordinatesByDate(LocalDateTime.of(2022,4,18,11,49), 2, 3).stream()
                .map(Coordinate::getLatitude)
                .toList());
        assertEquals(List.of(21.2, 41.2),activityDao.findTrackPointCoordinatesByDate(LocalDateTime.of(2022,4,30,11,49), 0, 2).stream()
                .map(Coordinate::getLatitude)
                .toList());
    }

    @Test
    @DisplayName("Insert TrackPoints Test")
    void insertTrackPointsTest(){
        TrackPoint trackpoint1=new TrackPoint(LocalDate.of(2022,4,30),1.0,2.0);
        TrackPoint trackpoint2=new TrackPoint(LocalDate.of(2022,4,1),2.2,3.9);

        List<TrackPoint> trackpoints=Arrays.asList(trackpoint1, trackpoint2);
        Activity activity=new Activity(LocalDateTime.of(1984, 11, 11, 21,49), "Kék túra", ActivityType.BASKETBALL);
        activity.addTrackPoint(trackpoint1);
        activity.addTrackPoint(trackpoint2);
        activityDao.saveActivity(activity);

        Activity activityReload=activityDao.findActivityByIdWithTrackPoints(activity.getId());

        assertEquals(List.of(LocalDate.of(2022,4,1), LocalDate.of(2022,4,30)),activityReload.getTrackPoints().stream()
                .map(TrackPoint::getTime)
                .toList());
    }

    @Test
    @DisplayName("Insert Activity and read from DB Test")
    void insertAndReadActivityTest(){
        Activity activity=activityDao.findById((long)2);
        assertEquals("Falmászás teszt",activity.getDescription());
    }

    @Test
    @DisplayName("List Activities from DB Test")
    void listActivitiesFromDBTest(){
        assertEquals(List.of("Éjszakai futás teszt", "Falmászás teszt", "Laza kosarazás teszt"),activityDao.listActivities().stream()
                .map(Activity::getDescription)
                .toList());
    }

    @Test
    @DisplayName("Update Activity Description Test")
    void updateActivityTest(){
        Activity activity=activityDao.findById((long)2);
        assertEquals("Falmászás teszt",activity.getDescription());
        long id=activity.getId();
        activityDao.changeDescription(id,"DAO-ban frissítettem");
        Activity activityNew=activityDao.findById(id);
        assertEquals("DAO-ban frissítettem",activityNew.getDescription());
    }

    @Test
    @DisplayName("Delete Activity Test")
    void deleteActivityTest(){
        Activity activity=activityDao.findById((long)1);
        assertEquals("Éjszakai futás teszt",activity.getDescription());
        long id=activity.getId();
        activityDao.delete(id);
        assertEquals(List.of("Falmászás teszt", "Laza kosarazás teszt"),activityDao.listActivities().stream()
                .map(Activity::getDescription)
                .toList());

    }

    @Test
    @DisplayName("Generated ids Test")
    void loopInsertTest(){
        for(int i=0;i<15;i++){
            activityDao.saveActivity(new Activity(LocalDateTime.of(1984, 11, 11, 21,49), "Laza kosarazás teszt", ActivityType.BASKETBALL));
        }
    }

    @Test
    @DisplayName("Create and Update Time Test")
    void createAndUpdateActivityTimeTest(){
        LocalDateTime created=LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        activityDao.saveActivity(new Activity(LocalDateTime.of(1976, 4, 24, 11,18), "Húsvéti kerékpártúra", ActivityType.BIKING));

        Activity activity=activityDao.findById((long)4);
        assertEquals("Húsvéti kerékpártúra",activity.getDescription());
        System.out.println(created+" ? "+activity.getCreatedAt());
        assertEquals(created,activity.getCreatedAt());
//        assertTrue(created.equals(activity.getCreatedAt())||created.isBefore(activity.getCreatedAt()));
//        assertTrue(activity.getCreatedAt().isBefore(created.plusSeconds(1)));

        long id=activity.getId();
        LocalDateTime updated=LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);;
        activityDao.changeDescription(id,"DAO-ban újabb frissítés");
        Activity activityNew=activityDao.findById(id);
        assertEquals("DAO-ban újabb frissítés",activityNew.getDescription());
        assertEquals(updated,activity.getCreatedAt());
    }

    @Test
    @DisplayName("Merge Test")
    void mergeTest() {
        Activity activity=new Activity(LocalDateTime.of(1976, 4, 24, 11,18), "Húsvéti kerékpártúra", ActivityType.BIKING);
        activityDao.saveActivity(activity);
        activity.setDescription("Kardió túra");
        activityDao.mergeActivity(activity);
        Activity activityNew=activityDao.findById(activity.getId());
        assertEquals("Kardió túra",activityNew.getDescription());


    }

    @Test
    @DisplayName("Labels Test")
    void labelsTest() {
        List<String> labels=new ArrayList<>(Arrays.asList("éjszakai futás", "városi futás"));
        activityDao.setLabels(2L,labels);

//        Activity activityNew=activityDao.findById(2L);
        Activity activityNew=activityDao.findActivityByIdWithLabels(2L);

        assertEquals(List.of("éjszakai futás", "városi futás"),activityNew.getLabels().stream()
                .toList());


    }
}