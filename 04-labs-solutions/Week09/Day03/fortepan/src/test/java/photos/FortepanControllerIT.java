package photos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FortepanControllerIT {

    @Autowired
    WebTestClient webClient;

    @Autowired
    FortepanService service;

    PhotoDto photo;

    @BeforeEach
    void init() {
        service.deleteAllPhotos();

        service.createPhotoWithDescriptionAndYear(new CreatePhotoWithDescriptionAndYearCommand("Ló a mezőn", 1921));
        photo = service.createPhotoWithDescription(new CreatePhotoWithDescriptionCommand("Nő fák alatt, napernyővel"));
        service.updatePhotoWithPhotographerAndYear(photo.getId(), new UpdatePhotoWithPhotographerAndYearCommand(1935, "Tóth Béla"));
        service.createPhotoWithDescription(new CreatePhotoWithDescriptionCommand("Örkény István a feleségével"));
    }

    @Test
    void testListAllPhotos() {
        // Itt írd meg a WebClient-es GET kérést és vizsgálj rá a visszérkezett PhotoDto listára!

        webClient
                .get()
                .uri("/api/photos")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PhotoDto.class)
                .value(list -> assertThat(list)
                        .hasSize(3)
                        .extracting(PhotoDto::getDescription)
                        .containsOnly("Ló a mezőn", "Nő fák alatt, napernyővel", "Örkény István a feleségével"));
    }

    @Test
    void testListAllPhotosWithParameter() {
        // Itt írd meg a WebClient-es GET kérést a photo nevű attribútum adataiból vett paraméterekkel,
        // és vizsgálj rá a visszérkezett PhotoDto listára!

        webClient
                .get()
                .uri(builder -> builder.path("/api/photos").queryParam("photographer", "Tóth Béla").queryParam("year", 1935).build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PhotoDto.class)
                .value(list -> assertThat(list)
                        .hasSize(1)
                        .extracting(PhotoDto::getDescription)
                        .containsOnly("Nő fák alatt, napernyővel"));
    }

    @Test
    void testFindById() {
        // Itt írd meg a WebClient-es GET kérést a photo nevű attribútum id-jával,
        // és vizsgálj rá a visszérkezett PhotoDto objektumra!

        webClient
                .get()
                .uri("/api/photos/{id}", photo.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(PhotoDto.class)
                .value(photo -> assertEquals("Nő fák alatt, napernyővel", photo.getDescription()));
    }
    @Test
    void testFindByWrongId() {
        webClient
                .get()
                .uri("/api/photos/11")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void testCreatePhotoWithDescription() {
        // Itt hozz létre úgy egy új fényképet, hogy a WebClient segítségével küldöd be a POST kérést, amely csak egy
        // leírást tartalmaz!
        // (Tehát nem a service valamelyik createX() metódusát hívod meg, mint ahogy a @BeforeEach metódusban látható.)
        // Vizsgáld is a visszaérkező objektumot!

        webClient.post()
                .uri("/api/photos/create-description")
                .bodyValue(new CreatePhotoWithDescriptionCommand("Külvárosi utca"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PhotoDto.class)
                .value(photo -> assertEquals("Külvárosi utca", photo.getDescription()))
                .value(photo -> assertEquals(0, photo.getYear()))
                .value(photo -> assertEquals(null, photo.getNameOfPhotographer()))
                .value(photo -> assertEquals(Collections.emptyList(), photo.getAdditionalInfo()));
    }
    @Test
    void testCreatePhotoWithEmptyDescription() {
        webClient.post()
                .uri("/api/photos/create-description")
                .bodyValue(new CreatePhotoWithDescriptionCommand(""))
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void testCreatePhotoWithDescriptionAndYear() {
        // Itt hozz létre úgy egy új fényképet, hogy a WebClient segítségével küldöd be a POST kérést, amely egy
        // leírást és egy évszámot tartalmaz!
        // (Tehát nem a service valamelyik createX() metódusát hívod meg, mint ahogy a @BeforeEach metódusban látható.)
        // Vizsgáld is a visszaérkező objektumot!

        webClient.post()
                .uri("/api/photos/create-description-and-year")
                .bodyValue(new CreatePhotoWithDescriptionAndYearCommand("Külvárosi utca", 1954))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PhotoDto.class)
                .value(photo -> assertEquals("Külvárosi utca", photo.getDescription()))
                .value(photo -> assertEquals(1954, photo.getYear()))
                .value(photo -> assertEquals(null, photo.getNameOfPhotographer()))
                .value(photo -> assertEquals(Collections.emptyList(), photo.getAdditionalInfo()));
    }
    @Test
    void testCreatePhotoWithDescriptionAndWrongYear() {
        webClient.post()
                .uri("/api/photos/create-description-and-year")
                .bodyValue(new CreatePhotoWithDescriptionAndYearCommand("Külvárosi utca", 1799))
                .exchange()
                .expectStatus()
                .isBadRequest();
    }
    @Test
    void testUpdatePhotoWithPhotographerAndYear() {
        // Írd meg a photo nevű attribútum módosítását a fotós nevével és az évszámmal,
        // és vizsgálj rá a visszakapott PhotoDto objektumra!

        webClient
                .put()
                .uri("/api/photos/photographer-and-year/{id}", photo.getId())
                .bodyValue(new UpdatePhotoWithPhotographerAndYearCommand(1946, "Kiss József"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PhotoDto.class)
                .value(photo -> assertEquals("Nő fák alatt, napernyővel", photo.getDescription()))
                .value(photo -> assertEquals(1946, photo.getYear()))
                .value(photo -> assertEquals("Kiss József", photo.getNameOfPhotographer()));
    }

    @Test
    void updatePhotoWithInfo() {
        // Írd meg a photo nevű attribútum módosítását valamilyen plusz infóval,
        // és vizsgálj rá a visszakapott PhotoDto objektumra!

        webClient
                .put()
                .uri("/api/photos/info/{id}", photo.getId())
                .bodyValue(new UpdatePhotoWithInfoCommand("erősen sérült"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PhotoDto.class)
                .value(photo -> assertEquals("Nő fák alatt, napernyővel", photo.getDescription()))
                .value(photo -> assertEquals("erősen sérült", photo.getAdditionalInfo().get(0)));
    }
    @Test
    void updatePhotoWithNoInfo() {
        webClient
                .put()
                .uri("/api/photos/info/{id}", photo.getId())
                .bodyValue(new UpdatePhotoWithInfoCommand(""))
                .exchange()
                .expectStatus()
                .isBadRequest();
    }
    @Test
    void testDeletePhoto() {
        // Írd meg a photo nevű attribútum törlését, majd vizsgálj rá, hogy tényleg
        // törlődött-e a listából (kérd le újra az összes elemet)!

        webClient
                .delete()
                .uri("/api/photos/{id}", photo.getId())
                .exchange()
                .expectStatus()
                .isNoContent();
        webClient
                .get()
                .uri("/api/photos")
                .exchange()
                .expectBodyList(PhotoDto.class)
                .value(list -> assertThat(list)
                        .hasSize(2)
                        .extracting(PhotoDto::getDescription)
                        .containsOnly("Ló a mezőn", "Örkény István a feleségével"));
    }
}