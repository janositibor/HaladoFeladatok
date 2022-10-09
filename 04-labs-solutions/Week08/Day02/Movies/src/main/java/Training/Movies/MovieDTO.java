package Training.Movies;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class MovieDTO {
    private Long id;
    private String title;
    private int length;
    private Double averageRating;
    private List<Integer> ratings;
}
