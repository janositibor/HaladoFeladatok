package TZJanosi.locations;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class LocationsController {
    private LocationsService locationsService;

    public LocationsController(LocationsService locationsService) {
        this.locationsService = locationsService;
    }

    @GetMapping("/locations")
    public String getLocations(){
        return locationsService.getLocations().toString() + "<br><br>Generated: "+ LocalDateTime.now();
    }
}
