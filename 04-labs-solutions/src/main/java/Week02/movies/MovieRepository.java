package Week02.movies;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class MovieRepository {

    private JdbcTemplate jdbcTemplate;


    public MovieRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public Movie saveMovie(Movie movie){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con ->{
            PreparedStatement ps =
                    con.prepareStatement("insert into movies(title,date_of_release,length) values(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,movie.getTitle());
            ps.setDate(2, Date.valueOf(movie.getReleaseDate()));
            ps.setInt(3, movie.getLength());
            return ps;
        }, keyHolder);

        return new Movie(keyHolder.getKey().longValue(),movie.getTitle(),movie.getReleaseDate(),movie.getLength());
    }
    public Optional<Movie> findByTitle(String title){
        List<Movie> result=jdbcTemplate.query("SELECT * FROM movies WHERE title = ?",new MovieMapper(), title);
        if(result.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }

}
