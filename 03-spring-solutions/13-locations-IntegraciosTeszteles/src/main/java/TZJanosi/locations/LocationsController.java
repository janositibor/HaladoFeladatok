package TZJanosi.locations;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.awt.*;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LocationsController {
    private LocationsService locationsService;

    public LocationsController(LocationsService locationsService) {
        this.locationsService = locationsService;
    }

    @GetMapping("/locations")
    public List<LocationDto> getLocations(){
        return locationsService.getLocations();
    }

    @GetMapping("/locationsLike")
    public List<LocationDto> getLocationsContains(@RequestParam Optional<String> contains){
        return locationsService.getLocationsContains(contains);
    }

    @GetMapping("/location/{id}")
    public LocationDto getLocationById(@PathVariable("id") long id){
        return locationsService.getLocationById(id);
    }

//    @GetMapping("/location/{id}")
//    public ResponseEntity getLocationById(@PathVariable("id") long id){
//        try{
//            return ResponseEntity.ok(locationsService.getLocationById(id).toString() + "<br><br>Generated: "+ LocalDateTime.now());
//        }
//        catch(IllegalArgumentException iae){
//            return ResponseEntity.notFound().build();
//        }
//    }

    @PostMapping("/location/create")
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDto createLocation(@RequestBody CreateLocationCommand createLocationCommand){
        return locationsService.createLocation(createLocationCommand);

    }

    @PutMapping("/location/update/{id}")
    public LocationDto updateLocation(@PathVariable("id") long id, @RequestBody UpdateLocationCommand updateLocationCommand){
        return locationsService.updateLocation(id, updateLocationCommand);
    }

    @DeleteMapping("/location/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocation(@PathVariable("id") long id){
        locationsService.deleteLocation(id);
    }


}
