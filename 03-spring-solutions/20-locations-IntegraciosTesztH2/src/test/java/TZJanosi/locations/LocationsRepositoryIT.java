package TZJanosi.locations;

import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LocationsRepositoryIT {
    @Autowired
    LocationRepository locationrepository;
    @Test
    void persistTest(){
        Location location=new Location(1L,"Otthon",45.1,85.3);
        locationrepository.save(location);
        List<Location> locations=locationrepository.findAll();
        assertThat(locations)
                .extracting(Location::getName)
                .hasSize(1)
                .containsOnly("Otthon");
    }
}
