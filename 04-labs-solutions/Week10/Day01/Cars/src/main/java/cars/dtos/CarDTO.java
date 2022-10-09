package cars.dtos;

import cars.model.Condition;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode
public class CarDTO {
    private Long id;
    private String brand;
    private String type;
    private int age;
    private Condition condition;
    private List<KilometerStateDTO> kilometerStateList=new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDTO carDTO = (CarDTO) o;
        return age == carDTO.age && Objects.equals(brand, carDTO.brand) && Objects.equals(type, carDTO.type) && condition == carDTO.condition && Objects.equals(kilometerStateList, carDTO.kilometerStateList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, type, age, condition, kilometerStateList);
    }
}
