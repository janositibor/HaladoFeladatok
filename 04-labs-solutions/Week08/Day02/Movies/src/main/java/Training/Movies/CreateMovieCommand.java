package Training.Movies;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMovieCommand {
    private String title;
    private int length;
}
