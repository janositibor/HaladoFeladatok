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

    @ExceptionHandler(LocationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Problem> handleNotFound(LocationNotFoundException lnfe){
        Problem problem=Problem.builder()
                .withType(URI.create("location/not-found"))
                .withTitle("Not found")
                .withStatus(Status.NOT_FOUND)
                .withDetail(lnfe.getMessage()
                )
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }
}
