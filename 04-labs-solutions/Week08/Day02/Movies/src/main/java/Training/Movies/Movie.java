package Training.Movies;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Getter
@Setter
@NoArgsConstructor
public class Movie {
    private Long id;
    private String title;
    private int length;
    private Double averageRating=null;
    private List<Integer> ratings=new ArrayList<>();

    public Movie(Long id, String title, int length) {
        this.id = id;
        this.title = title;
        this.length = length;
    }

    public Movie(String title, int length) {
        this.title = title;
        this.length = length;
    }
    public void addRating(Integer rating){
        ratings.add(rating);
        OptionalDouble average = ratings.stream()
                .mapToDouble(a -> a)
                .average();
        averageRating=average.isPresent() ? average.getAsDouble() : 0.0;
    }
}
