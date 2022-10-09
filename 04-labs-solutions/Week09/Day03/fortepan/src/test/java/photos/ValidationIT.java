package photos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ValidationIT {

    @Autowired
    WebTestClient webClient;

    @Autowired
    FortepanService service;

    @Test
    void testCreateWithNullString() {
        webClient.post()
                .uri("/api/photos/create-description")
                .bodyValue(new CreatePhotoWithDescriptionCommand(null))
                .exchange()
                .expectStatus()
                .isBadRequest();
        webClient.post()
                .uri("/api/photos/create-description-and-year")
                .bodyValue(new CreatePhotoWithDescriptionAndYearCommand(null, 1967))
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

   @Test
    void testCreateWithEmptyString() {
        webClient.post()
                .uri("/api/photos/create-description")
                .bodyValue(new CreatePhotoWithDescriptionCommand(""))
                .exchange()
                .expectStatus()
                .isBadRequest();
        webClient.post()
                .uri("/api/photos/create-description-and-year")
                .bodyValue(new CreatePhotoWithDescriptionAndYearCommand("\n", 1967))
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void testCreateWithWrongYear() {
        webClient.post()
                .uri("/api/photos/create-description-and-year")
                .bodyValue(new CreatePhotoWithDescriptionAndYearCommand("Lakodalom", 1799))
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void testSuccessfulCreate() {
        webClient.post()
                .uri("/api/photos/create-description")
                .bodyValue(new CreatePhotoWithDescriptionCommand("Lakodalom"))
                .exchange()
                .expectStatus()
                .isCreated();
        webClient.post()
                .uri("/api/photos/create-description-and-year")
                .bodyValue(new CreatePhotoWithDescriptionAndYearCommand("Lakodalom", 1967))
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void testUpdateWithNullOrEmptyAdditionalInfo() {
        PhotoDto photo = service.createPhotoWithDescriptionAndYear(
                new CreatePhotoWithDescriptionAndYearCommand("Lakodalom", 1876));

        webClient.put()
                .uri("/api/photos/info/{id}", photo.getId())
                .bodyValue(new UpdatePhotoWithInfoCommand(null))
                .exchange()
                .expectStatus()
                .isBadRequest();
        webClient.put()
                .uri("/api/photos/info/{id}", photo.getId())
                .bodyValue(new UpdatePhotoWithInfoCommand(""))
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void testNotFound() {
        webClient.get()
                .uri("/api/photos/{id}", 100)
                .exchange()
                .expectStatus()
                .isNotFound();
        webClient.put()
                .uri("/api/photos/photographer-and-year/{id}", 100)
                .bodyValue(new UpdatePhotoWithPhotographerAndYearCommand(1900, "name"))
                .exchange()
                .expectStatus()
                .isNotFound();
        webClient.put()
                .uri("/api/photos/info/{id}", 100)
                .bodyValue(new UpdatePhotoWithInfoCommand("info"))
                .exchange()
                .expectStatus()
                .isNotFound();
        webClient.delete()
                .uri("/api/photos/{id}", 100)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void testSuccessfulDelete() {
        PhotoDto photo = service.createPhotoWithDescriptionAndYear(
                new CreatePhotoWithDescriptionAndYearCommand("Lakodalom", 1876));

        webClient.delete()
                .uri("/api/photos/{id}", photo.getId())
                .exchange()
                .expectStatus()
                .isNoContent();
    }
}
