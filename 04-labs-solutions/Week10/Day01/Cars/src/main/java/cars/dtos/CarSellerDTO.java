package cars.dtos;

import cars.model.Car;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CarSellerDTO {
    private long id;
    private String name;
    private List<CarDTO> cars;
}
