package Training.Movies;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<MovieDTO> getMovies(@RequestParam(value="avg") Optional<Double> minAVG, @RequestParam(value="minRatingCount") Optional<Integer> minNumberOfRatings){
        return movieService.getAllMovies(minAVG,minNumberOfRatings);
    }
    @GetMapping("/criteria")
    public List<MovieDTO> getMoviesWithCriteria(MovieCriteria movieCriteria){
        return movieService.getAllMoviesWithCriteria(movieCriteria);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDTO createMovie(@RequestBody CreateMovieCommand createMovieCommand){
        return movieService.createMovie(createMovieCommand);
    }
    @GetMapping("/{id}")
    public MovieDTO getMovieByID(@PathVariable("id") long id){
        return movieService.getMovieByID(id);
    }
    @GetMapping("/{id}/ratings")
    public List<Integer> getRatingsByMovieID(@PathVariable("id") long id){
        return movieService.getRatingsByMovieID(id);
    }
    @PostMapping("/{id}/ratings")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Integer> addRatingToMovie(@PathVariable("id") long id, @RequestBody AddRatingCommand addRatingCommand){
        return movieService.addRatingToMovie(id,addRatingCommand);
    }
    @PutMapping("/{id}/update")
    public MovieDTO updateMovie(@PathVariable("id") long id, @RequestBody UpdateCommand updateCommand){
        return movieService.updateMovie(id,updateCommand);
    }
}
