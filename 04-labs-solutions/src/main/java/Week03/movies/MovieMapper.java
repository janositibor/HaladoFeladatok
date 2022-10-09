package Week03.movies;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
        Movie movie=new Movie(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getDate("date_of_release").toLocalDate(),
                rs.getInt("length")
        );
        return movie;
    }
}
