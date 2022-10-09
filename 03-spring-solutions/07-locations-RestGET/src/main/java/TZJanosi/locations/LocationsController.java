package TZJanosi.locations;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/locations")
public class LocationsController {
    private LocationsService locationsService;

    public LocationsController(LocationsService locationsService) {
        this.locationsService = locationsService;
    }

    @GetMapping
    public String getLocations(){
        return locationsService.getLocations().toString() + "<br><br>Generated: "+ LocalDateTime.now();
    }
}
