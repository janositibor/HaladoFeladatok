package cars.controller;

import cars.criteria.CarCriteria;
import cars.model.Car;
import cars.service.CarSellingService;
import cars.dtos.AddKilometerStatesCommand;
import cars.dtos.CarDTO;
import cars.dtos.CreateCarCommand;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    private CarSellingService carSellingService;

    public CarController(CarSellingService carSellingService) {
        this.carSellingService = carSellingService;
    }
    @GetMapping
    public List<CarDTO> getCars(CarCriteria carCriteria){
        return carSellingService.getCars(carCriteria);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO createCar(@Valid @RequestBody CreateCarCommand createCarCommand){
        return carSellingService.createCar(createCarCommand);
    }

    @GetMapping("/brands")
    public List<String> getBrands(){
        return carSellingService.getBrands();
    }

    @PostMapping("/{id}/kilometerstates")
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO addKilometerStatesToCar(@PathVariable("id") long id, @Valid @RequestBody AddKilometerStatesCommand addKilometerStatesCommand){
        return carSellingService.addKilometerStatesToCar(id,addKilometerStatesCommand);
    }

    @GetMapping("/{id}")
    public CarDTO getCar(@PathVariable("id") long id){
        return carSellingService.getCar(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocation(@PathVariable("id") long id){
        carSellingService.deleteCar(id);
    }
}
