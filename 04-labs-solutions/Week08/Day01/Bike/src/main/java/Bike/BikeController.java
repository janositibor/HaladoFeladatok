package Bike;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/rentals")
public class BikeController {
    private BikeService bikeService;

    public BikeController(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @GetMapping
    public List<BikeRentalDTO> getRentals(){
//        return bikeService.getRentals()+ "<br><br>Generated: "+ LocalDateTime.now();
        return bikeService.getAllRentals();

    }

    @GetMapping("/users")
    public Set<String> getUserIDs(){
//        return bikeService.getUserIDs()+ "<br><br>Generated: "+ LocalDateTime.now();
        return bikeService.getUserIDs();

    }

    @GetMapping("/{id}")
    public BikeRentalDTO getRentalByID(@PathVariable("id") int id){
//        return bikeService.getRentals()+ "<br><br>Generated: "+ LocalDateTime.now();
        return bikeService.getRentalByID(id);
    }

    @GetMapping("/from")
//    public List<BikeRentalDTO> getRentalsFrom(@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> start){
    public List<BikeRentalDTO> getRentalsFrom(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Optional<LocalDateTime> start){
        return bikeService.getRentalsFrom(start);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BikeRentalDTO createRental(@RequestBody CreateBikeRentalCommand createBikeRentalCommand){
        return bikeService.createRental(createBikeRentalCommand);

    }

}
