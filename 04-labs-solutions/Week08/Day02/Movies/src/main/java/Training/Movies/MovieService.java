package Training.Movies;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private AtomicLong idGenerator= new AtomicLong();
    private List<Movie> moviesList=new ArrayList<>();
    private ModelMapper modelMapper;

    public MovieService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<MovieDTO> getAllMovies() {
        return moviesList.stream()
                .map(m->modelMapper.map(m,MovieDTO.class))
                .collect(Collectors.toList());
    }


    public MovieDTO createMovie(CreateMovieCommand createMovieCommand) {
        Movie movie=modelMapper.map(createMovieCommand,Movie.class);
        movie.setId(idGenerator.incrementAndGet());
        moviesList.add(movie);
        return modelMapper.map(movie,MovieDTO.class);
    }

    public MovieDTO getMovieByID(long id) {
        return moviesList.stream()
                .filter(m->m.getId()==id)
                .map(m->modelMapper.map(m,MovieDTO.class))
                .findFirst().orElseThrow(()->new MovieNotFoundException("No movie with id: "+id));

    }


    public List<Integer> getRatingsByMovieID(long id) {
        return moviesList.stream()
                .filter(m->m.getId()==id)
                .map(Movie::getRatings)
                .findFirst().orElseThrow(()->new MovieNotFoundException("No movie with id: "+id));
    }

    public List<Integer> addRatingToMovie(long id, AddRatingCommand addRatingCommand) {
        Movie movie=moviesList.stream()
                .filter(m->m.getId()==id)
                .findFirst().orElseThrow(()->new MovieNotFoundException("No movie with id: "+id));

        movie.addRating(addRatingCommand.getRating());
        return getRatingsByMovieID(id);
    }
}
