package cars.controller;

import cars.dtos.CarSellerDTO;
import cars.dtos.CreateCarCommand;
import cars.dtos.CreateCarSellerCommand;
import cars.model.CarSeller;
import cars.service.CarSellingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/car-sellers")
public class CarSellerController {
    private CarSellingService carSellingService;

    public CarSellerController(CarSellingService carSellingService) {
        this.carSellingService = carSellingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarSellerDTO createCarSeller(@RequestBody CreateCarSellerCommand createCarSellerCommand){
        return carSellingService.createCarSeller(createCarSellerCommand);
    }

    @PostMapping("/{id}/cars")
    private CarSellerDTO addCarToSeller(@PathVariable long id, @RequestBody CreateCarCommand createCarCommand){
           return carSellingService.addCarToSeller(id,createCarCommand);
    }
}
