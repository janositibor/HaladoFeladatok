package Bike;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
public class BikeController {
    private BikeService bikeService;

    public BikeController(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @GetMapping("/history")
    public List<BikeRental> getRentals(){
//        return bikeService.getRentals()+ "<br><br>Generated: "+ LocalDateTime.now();
        return bikeService.getAllRentals();

    }

    @GetMapping("/users")
    public Set<String> getUserIDs(){
//        return bikeService.getUserIDs()+ "<br><br>Generated: "+ LocalDateTime.now();
        return bikeService.getUserIDs();

    }

}
