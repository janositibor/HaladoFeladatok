package photos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePhotoWithDescriptionAndYearCommand {

    @NotBlank(message ="Description mustn't be empty!")
    private String description;

    @Min(value = 1800L, message = "Year should not be before 1800!")
    private int year;
}
