package cars;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CarService {
    private AtomicLong idGenerator= new AtomicLong();
    private ModelMapper modelMapper;
    private List<Car> carsList=new ArrayList<>();

    public CarService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<CarDTO> getCars(CarCriteria carCriteria) {
        List<Car> filteredCarList= carsList.stream()
                .filter(m->(carCriteria.getBrand().equals("")||m.getBrand().equalsIgnoreCase(carCriteria.getBrand())))
                .filter(m->(m.getAge()<=carCriteria.getMaxAge()))
                .collect(Collectors.toList());

        return sortedCarStream(filteredCarList,carCriteria).stream()
                .map(m->modelMapper.map(m,CarDTO.class))
                .collect(Collectors.toList());
    }

    private List<Car> sortedCarStream(List<Car> filteredCarList, CarCriteria carCriteria) {
        List<Car> orderedCarList;
        if(carCriteria.getOrderBy()==OrderBy.age) {
            orderedCarList=filteredCarList.stream()
                    .sorted(Comparator.comparing(Car::getAge))
                    .collect(Collectors.toList());
        }
        else if(carCriteria.getOrderBy()==OrderBy.km) {
            orderedCarList=filteredCarList.stream()
                    .sorted(Comparator.comparing(car->car.getKilometerStateList().get(car.getKilometerStateList().size()-1).getKmCounter()))
                    .collect(Collectors.toList());
        }
        else{
            orderedCarList=filteredCarList.stream()
                    .sorted(Comparator.comparing(Car::getId))
                    .collect(Collectors.toList());
        }
        if(carCriteria.getOrderType()==OrderType.desc){
            Collections.reverse(orderedCarList);
        }
        return orderedCarList;
    }

    public CarDTO createCar(CreateCarCommand createCarCommand) {
        Car car = new Car();
        car.setId(idGenerator.incrementAndGet());
        car.setBrand(createCarCommand.getBrand());
        car.setType(createCarCommand.getType());
        car.setAge(createCarCommand.getAge());
        car.setCondition(createCarCommand.getCondition());
        car.addKilometerCount(createCarCommand.getKmCounter());
        carsList.add(car);
        return modelMapper.map(car,CarDTO.class);
    }

    public List<String> getBrands() {
        return carsList.stream()
                .map(Car::getBrand)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public CarDTO addKilometerStatesToCar(long id, AddKilometerStatesCommand addKilometerStatesCommand) {
        Car car=findCarById(id);
        if(car.getKilometerStateList().get(car.getKilometerStateList().size()-1).getKmCounter()>addKilometerStatesCommand.getKmCounter()){
//            throw new IllegalStateException("Not valid km count: "+addKilometerStatesCommand.getKmCounter());
            throw new IllegalKmStateException(addKilometerStatesCommand.getKmCounter());
        }
        car.addKilometerCount(addKilometerStatesCommand.getKmCounter());
        return modelMapper.map(car,CarDTO.class);
    }

    private Car findCarById(long id) {
        return carsList.stream()
                .filter(c->c.getId()==id)
                .findFirst().orElseThrow(()->new CarNotFoundException(id));
    }

    public CarDTO getCar(long id) {
        Car car=findCarById(id);
        return modelMapper.map(car,CarDTO.class);
    }

    public void deleteCar(long id) {
        Car car=findCarById(id);
        carsList.remove(car);
    }
}
