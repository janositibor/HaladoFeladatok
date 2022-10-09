package photos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoDto {

    private Long id;

    private String description;

    private int year;

    private String nameOfPhotographer;

    private List<String> additionalInfo;

    public PhotoDto(String description, int year) {
        this.description = description;
        this.year = year;
    }
}
