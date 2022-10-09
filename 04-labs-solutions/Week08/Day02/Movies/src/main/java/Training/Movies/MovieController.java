package Training.Movies;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<MovieDTO> getMovies(){
        return movieService.getAllMovies();
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
}
