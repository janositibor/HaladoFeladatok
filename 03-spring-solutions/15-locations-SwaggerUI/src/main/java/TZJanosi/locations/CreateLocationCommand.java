package TZJanosi.locations;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateLocationCommand {
    @Schema(description = "Name of Location",example = "Könyvtár")
    private String name;
    @Schema(description = "Latitude of Location",example = "13.19")
    private double lat;
    @Schema(description = "Longitude of Location",example = "83.22")
    private double lon;
}
