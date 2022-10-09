package cars.service;

import cars.criteria.CarCriteria;
import cars.dtos.*;
import cars.exceptions.CarNotFoundException;
import cars.exceptions.CarSellerNotFoundException;
import cars.exceptions.IllegalKmStateException;
import cars.model.Car;
import cars.model.CarSeller;
import cars.model.OrderBy;
import cars.model.OrderType;
import cars.repository.CarsRepository;
import cars.repository.CarsellersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class CarSellingService {
    private ModelMapper modelMapper;
    private CarsRepository carsRepository;
    private CarsellersRepository carsellersRepository;

    public CarSellingService(ModelMapper modelMapper, CarsRepository carsRepository, CarsellersRepository carsellersRepository) {
        this.modelMapper = modelMapper;
        this.carsRepository = carsRepository;
        this.carsellersRepository = carsellersRepository;
    }

    public List<CarDTO> getCars(CarCriteria carCriteria) {
        List<Car> filteredCarList=new ArrayList<>();
        if(!carCriteria.getBrand().equals("")){
            filteredCarList=carsRepository.findAllByBrandIgnoreCaseAndAgeIsLessThanEqual(carCriteria.getBrand(),carCriteria.getMaxAge());
        }
        else{
            filteredCarList=carsRepository.findAllByAgeIsLessThanEqual(carCriteria.getMaxAge());
        }

        return sortedCarStream(filteredCarList,carCriteria).stream()
                .map(m->modelMapper.map(m,CarDTO.class))
                .collect(Collectors.toList());
    }

    private List<Car> sortedCarStream(List<Car> filteredCarList, CarCriteria carCriteria) {
        List<Car> orderedCarList;
        if(carCriteria.getOrderBy()== OrderBy.age) {
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
        if(carCriteria.getOrderType()== OrderType.desc){
            Collections.reverse(orderedCarList);
        }
        return orderedCarList;
    }

    public CarDTO createCar(CreateCarCommand createCarCommand) {
        Car car = new Car();
        car.setBrand(createCarCommand.getBrand());
        car.setType(createCarCommand.getType());
        car.setAge(createCarCommand.getAge());
        car.setCondition(createCarCommand.getCondition());
        car.addKilometerCount(createCarCommand.getKmCounter());
        carsRepository.save(car);
        return modelMapper.map(car,CarDTO.class);
    }

    public List<String> getBrands() {
        return carsRepository.findDistinctByBrand().stream()
                .map(Car::getBrand)
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
        carsRepository.save(car);
        return modelMapper.map(car,CarDTO.class);
    }

    private Car findCarById(long id) {
        Optional<Car> car=carsRepository.findById(id);
        if(car.isEmpty()){
//            throw  new IllegalArgumentException("No car found with id: "+id);
            throw  new CarNotFoundException(id);
        }
        return car.get();
    }

    public CarDTO getCar(long id) {
        Car car=findCarById(id);
        return modelMapper.map(car,CarDTO.class);
    }

    public void deleteCar(long id) {
        Car car=findCarById(id);
        carsRepository.delete(car);
    }
    public void deleteAllCar() {
        carsRepository.deleteAll();
    }

    public CarSellerDTO createCarSeller(CreateCarSellerCommand createCarSellerCommand) {
        CarSeller carSeller=new CarSeller(createCarSellerCommand.getName());
        carsellersRepository.save(carSeller);
        return modelMapper.map(carSeller,CarSellerDTO.class);
    }
    private CarSeller findCarSellerById(long id) {
        Optional<CarSeller> carSeller=carsellersRepository.findById(id);
        if(carSeller.isEmpty()){
//            throw  new IllegalArgumentException("No car found with id: "+id);
            throw  new CarSellerNotFoundException(id);
        }
        return carSeller.get();
    }

//    @Transactional
    public CarSellerDTO addCarToSeller(long id, CreateCarCommand createCarCommand) {
        CarSeller carSeller=findCarSellerById(id);

        Car car = new Car();
        car.setBrand(createCarCommand.getBrand());
        car.setType(createCarCommand.getType());
        car.setAge(createCarCommand.getAge());
        car.setCondition(createCarCommand.getCondition());
        car.addKilometerCount(createCarCommand.getKmCounter());
        car.setSeller(carSeller);
        carsRepository.save(car);

        return modelMapper.map(carSeller,CarSellerDTO.class);
    }
}
