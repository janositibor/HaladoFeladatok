package TZJanosi.locations;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateLocationCommand {
    @Schema(description = "Name of Location for Update",example = "KIlátó")
    private String name;
    @Schema(description = "Latitude of Location for Update",example = "1.98")
    private double lat;
    @Schema(description = "Longitude of Location for Update",example = "4.17")
    private double lon;
}
