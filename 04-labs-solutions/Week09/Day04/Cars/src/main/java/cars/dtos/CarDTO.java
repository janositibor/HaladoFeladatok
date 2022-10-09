package cars.dtos;

import cars.model.Condition;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CarDTO {
    private Long id;
    private String brand;
    private String type;
    private int age;
    private Condition condition;
    private List<KilometerStateDTO> kilometerStateList=new ArrayList<>();


}
