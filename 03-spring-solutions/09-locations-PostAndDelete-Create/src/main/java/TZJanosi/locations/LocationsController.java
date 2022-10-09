package TZJanosi.locations;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LocationsController {
    private LocationsService locationsService;

    public LocationsController(LocationsService locationsService) {
        this.locationsService = locationsService;
    }

    @GetMapping("/locations")
    public String getLocations(){
        return locationsService.getLocations().toString() + "<br><br>Generated: "+ LocalDateTime.now();
    }

    @GetMapping("/locationsLike")
    public String getLocationsContains(@RequestParam Optional<String> contains){
        return locationsService.getLocationsContains(contains).toString() + "<br><br>Generated: "+ LocalDateTime.now();
    }

    @GetMapping("/location/{id}")
    public String getLocationById(@PathVariable("id") long id){
        return locationsService.getLocationById(id).toString() + "<br><br>Generated: "+ LocalDateTime.now();
    }

    @PostMapping("/location/create")
    public LocationDto createLocation(@RequestBody CreateLocationCommand createLocationCommand){
        return locationsService.createLocation(createLocationCommand);

    }
}
