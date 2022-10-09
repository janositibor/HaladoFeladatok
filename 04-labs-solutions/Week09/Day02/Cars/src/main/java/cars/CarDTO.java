package cars;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
