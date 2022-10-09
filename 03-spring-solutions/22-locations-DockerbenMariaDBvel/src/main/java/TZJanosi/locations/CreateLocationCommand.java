package TZJanosi.locations;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateLocationCommand {
    @Schema(description = "Name of Location",example = "Könyvtár")
    @NotBlank(message ="Name mustn't be blank!")
    private String name;
    @Schema(description = "Latitude of Location",example = "13.19")
    @DecimalMax(value = "90.0", inclusive = true, message ="Max value is 90.0")
    @DecimalMin(value = "-90.0", inclusive = true, message ="Min value is -90.0")
    private double lat;
    @Schema(description = "Longitude of Location",example = "83.22")
    @DecimalMax(value = "180.0", inclusive = true, message ="Max value is 180.0")
    @DecimalMin(value = "-180.0", inclusive = true, message ="Min value is -180.0")
    private double lon;
}
