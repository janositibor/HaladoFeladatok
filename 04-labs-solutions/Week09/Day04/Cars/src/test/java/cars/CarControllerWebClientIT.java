package cars;

import cars.dtos.CarDTO;
import cars.dtos.CreateCarCommand;
import cars.dtos.KilometerStateDTO;
import cars.model.Condition;
import cars.service.CarSellingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarControllerWebClientIT {
    @Autowired
    WebTestClient webTestClient;

    @Autowired
    CarSellingService carSellingService;

    @BeforeEach
    void init(){
        carSellingService.deleteAllCar();
        webTestClient
                .post()
                .uri("/api/cars")
                .bodyValue(new CreateCarCommand("Toyota","Corolla",25, Condition.POOR,295000))
                .exchange();
        webTestClient
                .post()
                .uri("/api/cars")
                .bodyValue(new CreateCarCommand("Toyota","Corolla",15,Condition.EXCELLENT,95000))
                .exchange();
        webTestClient
                .post()
                .uri("/api/cars")
                .bodyValue(new CreateCarCommand("BMW","X3",19,Condition.NORMAL,350000))
                .exchange();
        webTestClient
                .post()
                .uri("/api/cars")
                .bodyValue(new CreateCarCommand("Toyota","Auris",5,Condition.EXCELLENT,55892))
                .exchange();
        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("api/cars").queryParam("brand","toyota").build())
                .exchange()
                .expectBodyList(CarDTO.class)
                .hasSize(3)
                .contains(new CarDTO(4L,"Toyota","Auris",5,Condition.EXCELLENT, List.of(new KilometerStateDTO(55892, LocalDate.now()))));

    }

    @Test
    void testCreateCar(){
        webTestClient
                .post()
                .uri("/api/cars")
                .bodyValue(new CreateCarCommand("Toyota","Corolla",25,Condition.POOR,295000))
                .exchange()
                .expectStatus().isCreated()
//                .expectBody(String.class).value(l-> System.out.println(l));
//                .expectBody().jsonPath("type").isEqualTo("Corolla")
//                .expectBody().jsonPath("lat").isEqualTo(46.1)
                .expectBody(CarDTO.class)
                .value(c->assertThat(c.getBrand()).isEqualTo("Toyota"))
        ;
    }
    @Test
    void testCreateCarOtherStyle(){
        EntityExchangeResult<CarDTO> result=webTestClient
                .post()
                .uri("/api/cars")
                .bodyValue(new CreateCarCommand("Toyota","Corolla",25,Condition.POOR,295000))
                .exchange()
                .expectBody(CarDTO.class)
                .returnResult();
        assertThat(result.getResponseBody().getType()).isEqualTo("Corolla");
    }
    @Test
    void testGetCarsWithBrand(){
        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("api/cars").queryParam("brand","toyota").build())
                .exchange()
                .expectBodyList(CarDTO.class)
                .hasSize(3)
                .contains(new CarDTO(4L,"Toyota","Auris",5,Condition.EXCELLENT, List.of(new KilometerStateDTO(55892, LocalDate.now()))));
    }
    @Test
    void testGetBrands(){
        List<String> result = webTestClient
                .get()
                .uri("/api/cars/brands")
                .exchange()
                .expectBody(List.class)
                .returnResult()
                .getResponseBody();

        assertThat(result)
                .hasSize(2)
                .containsOnly("Toyota","BMW")
        ;

    }

}