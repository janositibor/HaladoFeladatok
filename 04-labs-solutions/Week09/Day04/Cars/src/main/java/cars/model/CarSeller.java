package cars.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="car_sellers")
public class CarSeller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
//    @Column(name="seller_name")
    private String name;
    @OneToMany(mappedBy="seller")
    private List<Car> cars=new ArrayList<>();

    public CarSeller(String name) {
        this.name = name;
    }

//    public void addCar(Car car){
//        cars.add(car);
//    }
}
