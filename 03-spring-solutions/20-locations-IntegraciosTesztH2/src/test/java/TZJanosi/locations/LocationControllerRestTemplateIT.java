package TZJanosi.locations;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocationControllerRestTemplateIT {
    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    LocationsService locationsService;

    @Test
    void testCreateLocation(){
        LocationDto created=restTemplate.postForObject("/api/location/create", new CreateLocationCommand("Könyvtár",15.15,16.78),LocationDto.class);
        assertEquals(created.getName(),"Könyvtár");
    }

//    @Test
    @RepeatedTest(2)
    void testListLocations(){
        locationsService.deleteAllLocation();
        restTemplate.postForObject("/api/location/create", new CreateLocationCommand("Könyvtár",15.15,16.78),LocationDto.class);
        restTemplate.postForObject("/api/location/create", new CreateLocationCommand("Kilátó",13.44,16.87),LocationDto.class);
        List<LocationDto> locations=restTemplate.exchange("/api/locations", HttpMethod.GET, null, new ParameterizedTypeReference<List<LocationDto>>() {
        }).getBody();

        assertThat(locations)
                .extracting(LocationDto::getName)
                .hasSize(2)
                .containsOnly("Könyvtár", "Kilátó");
    }
}
