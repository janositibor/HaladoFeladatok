package photos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePhotoWithPhotographerAndYearCommand {

    private int year;

    private String nameOfPhotographer;
}
