package TZJanosi.locations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationsControllerTest {
    @Mock
    LocationsService locationsService;

    @InjectMocks
    LocationsController locationsControllerByMock;

    @Test
    @DisplayName("Test for the Controller class")
    void controllerTest(){
        List<Location> locations= Arrays.asList(new Location(1L,"Klub",46.1,85.3), new Location(2L,"Müv.ház",46.2,75.4));
        when(locationsService.getLocations()).thenReturn(locations);
        String answer=locationsControllerByMock.getLocations();
        assertThat(answer).startsWith(locations.toString());
    }

}