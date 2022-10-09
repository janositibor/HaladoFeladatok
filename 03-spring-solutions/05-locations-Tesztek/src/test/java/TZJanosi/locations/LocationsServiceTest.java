package TZJanosi.locations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LocationsServiceTest {
    @Test
    @DisplayName("Test for Service")
    void getLocationFromServiceTest(){
        LocationsService locationsService=new LocationsService();
        assertThat(locationsService.getLocations())
                .extracting(Location::getName)
                .hasSize(2)
                .containsOnly("Otthon", "Munkahely");
    }

}