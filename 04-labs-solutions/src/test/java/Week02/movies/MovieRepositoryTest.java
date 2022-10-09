package Week02.movies;

import org.assertj.core.api.Assertions;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MovieRepositoryTest {
    Flyway flyway;
    MovieRepository repository;

    @BeforeEach
    void init(){
        JdbcDataSource dataSource = new JdbcDataSource();

        dataSource.setUrl("jdbc:h2:~/test");
        dataSource.setUser("sa");
        dataSource.setPassword("sa");

        flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        repository = new MovieRepository(dataSource);
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
    void repoNotFondByTitleTest(){
        repository.saveMovie(new Movie("Sátántangó", LocalDate.of(1994,1,2),450));

        Optional<Movie> result = repository.findByTitle("Stántangó");

        Assertions.assertThat(result).isEqualTo(Optional.empty());

    }


}