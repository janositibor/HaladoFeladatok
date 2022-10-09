package cars.repository;

import cars.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarsRepository extends JpaRepository<Car,Long> {
    List<Car> findAllByBrandIgnoreCaseAndAgeIsLessThanEqual(String brand, int maxAge);
    List<Car> findAllByAgeIsLessThanEqual(int maxAge);

//    @Query("SELECT c.brand FROM cars c GROUP BY c.brand")
    @Query(value = "SELECT  DISTINCT brand FROM cars", nativeQuery = true)
    List<String> getAllBrands();
}
