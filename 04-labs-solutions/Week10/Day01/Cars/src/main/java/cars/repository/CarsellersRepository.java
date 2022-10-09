package cars.repository;

import cars.model.Car;
import cars.model.CarSeller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarsellersRepository extends JpaRepository<CarSeller,Long> {
}
