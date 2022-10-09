package Week03.movies;

import Week02.movies.Movie;
import Week02.movies.MovieRepository;
import Week02.movies.MovieService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {
    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    MovieService movieService;

    @Test
    @DisplayName("Wrong Insert Test")
    void InsertMovieWithWrongDateTest(){
        LocalDate dateToFailTest=LocalDate.of(1822,4,21);
        Week02.movies.Movie movie = new Week02.movies.Movie("Title", dateToFailTest,96);

        assertThatThrownBy(()->movieService.saveMovie("Title", dateToFailTest,96))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Date is not correct: 1822-04-21");
        verify(movieRepository, never()).saveMovie(any());
    }

    @Test
    @DisplayName("Insert Movie Test")
    void InsertMovieTest(){
        LocalDate dateToOKTest=LocalDate.of(2022,4,21);

        when(movieRepository.saveMovie(any(Week02.movies.Movie.class))).thenReturn(
                new Week02.movies.Movie(1L,"Title", dateToOKTest,96)
        );

        Week02.movies.Movie savedMovie=movieService.saveMovie("Title", dateToOKTest,96);

        assertThat(savedMovie.getId()).isEqualTo(1L);
        verify(movieRepository).saveMovie(argThat(m->m.getTitle().equals("Title")));
        verify(movieRepository).saveMovie(argThat(m->m.getReleaseDate().equals(dateToOKTest)));
        verify(movieRepository).saveMovie(argThat(m->m.getLength()==96));
    }

    @Test
    @DisplayName("Find Movie Test")
    void FindMovieTest() {
        LocalDate dateToOKTest=LocalDate.of(2022,4,21);

        when(movieRepository.findByTitle("Title")).thenReturn(
                Optional.of(new Week02.movies.Movie(1L,"Title", dateToOKTest,96))
        );

        Movie foundMovie=movieService.findMovieByTitle("Title");

        assertThat(foundMovie.getId()).isEqualTo(1L);
        assertThat(foundMovie.getTitle()).isEqualTo("Title");
    }

    @Test
    @DisplayName("Not Found Movie Test")
    void NotFoundMovieTest() {
        when(movieRepository.findByTitle("SomethingElse")).thenReturn(
                Optional.empty()
        );

        assertThatThrownBy(()->movieService.findMovieByTitle("SomethingElse"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No movie with title: SomethingElse");
    }

}