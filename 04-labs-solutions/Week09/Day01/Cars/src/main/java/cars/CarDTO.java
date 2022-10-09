package cars;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    private String id;
    private String brand;
    private String type;
    private int age;
    private Condition condition;
    private List<KilometerStateDTO> kilometerStateList=new ArrayList<>();
}
