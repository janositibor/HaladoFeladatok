package TZJanosi.locations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.when;

@SpringBootTest
class LocationsIT {

    @Autowired
    LocationsController locationsControllerBySpring;

    @Test
    @DisplayName("Integrity Test")
    void controllerTest(){
        List<LocationDto> locations= Arrays.asList(new LocationDto(1L,"Otthon",45.1,85.3), new LocationDto(2L,"Munkahely",46.2,75.4));
        List<LocationDto> answer=locationsControllerBySpring.getLocations();
        assertThat(answer)
                .hasSize(2)
                .map(LocationDto::getName)
                .containsOnly("Otthon","Munkahely");
     }
}
