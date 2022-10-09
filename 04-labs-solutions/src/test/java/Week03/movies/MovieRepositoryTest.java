package Week03.movies;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MovieRepositoryTest {
    MovieRepository repository;
    EntityManagerFactory emFactory;

    @BeforeEach
    void init(){
        emFactory= Persistence.createEntityManagerFactory("test-pu");
//        emFactory= Persistence.createEntityManagerFactory("pu");
        repository=new MovieRepository(emFactory);
    }

    @AfterEach
    void closeEntityManagerFactory(){
        emFactory.close();
    }

    @Test
    @DisplayName("Repo Insert Test")
    void testSaveMovie(){
        Movie result = repository.saveMovie(new Movie("Sátántangó", LocalDate.of(1994,1,2),450));

        assertThat(result.getId()).isNotEqualTo(null);
        assertThat(result.getTitle()).isEqualTo("Sátántangó");
    }

    @Test
    @DisplayName("Repo FindByTitle Test")
    void repoFindByTitleTest(){
        repository.saveMovie(new Movie("Sátántangó", LocalDate.of(1994,1,2),450));

        Optional<Movie> result = repository.findByTitle("Sátántangó");

        assertThat(result.get().getId()).isNotEqualTo(null);
        assertThat(result.get().getLength()).isEqualTo(450);
        assertThat(result.get().getTitle()).isEqualTo("Sátántangó");
        assertThat(result.get().getReleaseDate()).isEqualTo(LocalDate.of(1994,1,2));
    }

    @Test
    @DisplayName("Repo Movie not Found ByTitle Test")
    void repoNotFoundByTitleTest(){
        repository.saveMovie(new Movie("Sátántangó", LocalDate.of(1994,1,2),450));

        Optional<Movie> result = repository.findByTitle("Stántangó");

        Assertions.assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("Find Movie with Ratings Test")
    void testFindByTitleWithRatings(){
        Movie movie = new Movie("Sátántangó", LocalDate.of(1994,1,2),450);
        movie.addRating(new Rating(6.7,"user1"));
        movie.addRating(new Rating(6.9,"user2"));
        repository.saveMovie(movie);

        Optional<Movie> result = repository.findByTitleWithRatings("Sátántangó");

        assertThat(result.get().getLength()).isEqualTo(450);
        assertThat(result.get().getRatings()).extracting(Rating::getRating).containsExactly(6.7,6.9);
    }

    @Test
    @DisplayName("Not found Movie with Ratings Test")
    void testNotFoundByTitleWithRatings(){
        repository.saveMovie(new Movie("Sátántangó", LocalDate.of(1994,1,2),450));

        Optional<Movie> result = repository.findByTitleWithRatings("Stántangó");

        Assertions.assertThat(result).isEqualTo(Optional.empty());
    }
}