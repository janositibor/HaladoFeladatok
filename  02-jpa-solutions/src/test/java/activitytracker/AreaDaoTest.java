package activitytracker;

import net.bytebuddy.build.ToStringPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AreaDaoTest {
    private EntityManagerFactory factory;
    private AreaDao areaDao;
    private ActivityDao activityDao;

    @BeforeEach
    void init(){
        factory= Persistence.createEntityManagerFactory("pu");
        activityDao=new ActivityDao(factory);
        areaDao=new AreaDao(factory);
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
    @DisplayName("Test m x n")
    void insertAreasTest(){

        Activity activity1=activityDao.findActivityByIdWithAreas((long)1);
        Activity activity2=activityDao.findActivityByIdWithAreas((long)2);
        Activity activity3=activityDao.findActivityByIdWithAreas((long)3);

        Area area1=new Area("Ledina");
        Area area2=new Area("Tiborfalva");
        Area area3=new Area("Szecső");

        area1.addActivity(activity1);
        area1.addActivity(activity2);
        area1.addActivity(activity3);

        area2.addActivity(activity1);
        area2.addActivity(activity3);

        area3.addActivity(activity2);

        areaDao.saveArea(area1);
        areaDao.saveArea(area2);
        areaDao.saveArea(area3);

        Activity reloadActivity1=activityDao.findActivityByIdWithAreas(activity1.getId());
        Activity reloadActivity2=activityDao.findActivityByIdWithAreas(activity2.getId());
        Activity reloadActivity3=activityDao.findActivityByIdWithAreas(activity3.getId());


        assertThat(reloadActivity1.getAreas())
                .extracting(Area::getName)
                .hasSize(2)
                .containsOnly("Ledina","Tiborfalva");

        assertThat(reloadActivity2.getAreas())
                .extracting(Area::getName)
                .hasSize(2)
                .containsOnly("Ledina","Szecső");

        assertThat(reloadActivity3.getAreas())
                .extracting(Area::getName)
                .hasSize(2)
                .containsOnly("Ledina","Tiborfalva");

    }

    @Test
    @DisplayName("Test map")
    void mapTest(){

        Area area1=new Area("Ledina");
        Area area2=new Area("Tiborfalva");
        Area area3=new Area("Szecső");

        City city1=new City("Rigóder", 2500);
        City city2=new City("Pappkert", 1500);
        City city4=new City("Dunaszekcső", 4000);
        City city5=new City("Telep", 6000);

        city1.addToArea(area1);
        city2.addToArea(area1);
        city4.addToArea(area3);
        city5.addToArea(area3);

        areaDao.saveArea(area1);
        areaDao.saveArea(area2);
        areaDao.saveArea(area3);

        Area reloadArea1=areaDao.findByIdWithCities(area1.getId());
        Area reloadArea2=areaDao.findByIdWithCities(area2.getId());
        Area reloadArea3=areaDao.findByIdWithCities(area3.getId());


        assertThat(reloadArea1.getCities().keySet())
                .hasSize(2)
                .containsOnly("Rigóder","Pappkert");

        assertThat(reloadArea3.getCities().keySet())
                .hasSize(2)
                .containsOnly("Telep","Dunaszekcső");
    }
}