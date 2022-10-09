package cars;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }
    @GetMapping
    public List<CarDTO> getCars(CarCriteria carCriteria){
        return carService.getCars(carCriteria);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO createCar(@RequestBody CreateCarCommand createCarCommand){
        return carService.createCar(createCarCommand);
    }
    @GetMapping("/brands")
    public List<String> getBrands(){
        return carService.getBrands();
    }
    @PostMapping("/{id}/kilometerstates")
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO addKilometerStatesToCar(@PathVariable("id") long id, @RequestBody AddKilometerStatesCommand addKilometerStatesCommand){
        return carService.addKilometerStatesToCar(id,addKilometerStatesCommand);
    }
    @GetMapping("/{id}")
    public CarDTO getCar(@PathVariable("id") long id){
        return carService.getCar(id);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocation(@PathVariable("id") long id){
        carService.deleteCar(id);
    }
}
