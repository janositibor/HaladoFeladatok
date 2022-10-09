package TZJanosi.locations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="Operations on Locations")
public class LocationsController {
    private LocationsService locationsService;

    public LocationsController(LocationsService locationsService) {
        this.locationsService = locationsService;
    }

    @Operation(summary = "List all of locations")
    @ApiResponse(responseCode = "200", description = "Done")
    @GetMapping("/locations")
    public List<LocationDto> getLocations(){
        return locationsService.getLocations();
    }

    @Operation(summary = "Find Location which contains ")
    @ApiResponse(responseCode = "200", description = "Location has been found")
    @GetMapping("/locationsLike")
    public List<LocationDto> getLocationsContains(@RequestParam  @Parameter(name = "contains", description = "Substring to contains", example = "otth") Optional<String> contains){
        return locationsService.getLocationsContains(contains);
    }

    @Operation(summary = "Find Location By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location has been found"),
            @ApiResponse(responseCode = "404", description = "No Location found with this ID")
    })
    @GetMapping("/location/{id}")
    public LocationDto getLocationById(@PathVariable("id") @Parameter(name = "id", description = "Location id", example = "1") long id){
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
    @Operation(summary = "Create Location")
    @ApiResponse(responseCode = "201", description = "Location has been created")
    public LocationDto createLocation(@RequestBody CreateLocationCommand createLocationCommand){
        return locationsService.createLocation(createLocationCommand);

    }

    @Operation(summary = "Update Location By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location has been found"),
            @ApiResponse(responseCode = "404", description = "No Location found with this ID")
    })
    @PutMapping("/location/update/{id}")
    public LocationDto updateLocation(@PathVariable("id") @Parameter(name = "id", description = "Location ID to update", example = "2") long id, @RequestBody UpdateLocationCommand updateLocationCommand){
        return locationsService.updateLocation(id, updateLocationCommand);
    }

    @Operation(summary = "Delete Location By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Location has been deleted"),
            @ApiResponse(responseCode = "404", description = "No Location found with this ID")
    })
    @DeleteMapping("/location/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocation(@PathVariable("id") @Parameter(name = "id", description = "Location ID to delete", example = "2") long id){
        locationsService.deleteLocation(id);
    }


}
