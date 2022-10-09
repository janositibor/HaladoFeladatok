package TZJanosi.locations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocationsControllerWebClientIT {
    @MockBean
    LocationsService locationsService;
    @Autowired
    WebTestClient webTestClient;

    @Test
    void testCreateLocation(){
        when(locationsService.createLocation(any()))
                .thenReturn(new LocationDto(1L,"Klub",46.1,85.3));
        webTestClient
                .post()
                .uri("/api/location/create")
                .bodyValue(new CreateLocationCommand("Klub",47.1,88.3))
                .exchange()
                .expectStatus().isCreated()
//                .expectBody(String.class).value(l-> System.out.println(l));
//                .expectBody().jsonPath("name").isEqualTo("Klub")
//                .expectBody().jsonPath("lat").isEqualTo(46.1)
                .expectBody(LocationDto.class).value(l->assertEquals("Klub", l.getName()))
        ;
    }
    @Test
    void testFindLocationByID(){
        when(locationsService.getLocationById(1L))
                .thenReturn(new LocationDto(1L,"Klub",46.1,85.3));
        webTestClient
                .get()
                .uri("/api/location/{id}",1)
                .exchange()
                .expectBody(LocationDto.class).value(l->assertEquals("Klub", l.getName()));

    }
    @Test
    void testGetLocations(){
        when(locationsService.getLocations())
                .thenReturn(List.of(new LocationDto(1L,"Klub",46.1,85.3), new LocationDto(2L,"Müv.ház",46.2,75.4)));

        webTestClient
                .get()
                .uri("/api/locations")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(LocationDto.class)
                .hasSize(2)
                .contains(new LocationDto(1L,"Klub",46.1,85.3));
    }
    @Test
    void testFilter(){
        when(locationsService.getLocationsContains(any()))
                .thenReturn(List.of(new LocationDto(1L,"Klub",46.1,85.3), new LocationDto(2L,"Müv.ház",46.2,75.4)));

        webTestClient
                .get()
                .uri(builder -> builder.path("/api/locationsLike").queryParam("contains","Barmi").build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(LocationDto.class)
                .hasSize(2)
                .contains(new LocationDto(2L,"Müv.ház",46.2,75.4));
    }
}
