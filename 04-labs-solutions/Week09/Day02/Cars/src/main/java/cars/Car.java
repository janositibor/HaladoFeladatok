package cars;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private Long id;
    private String brand;
    private String type;
    private int age;
    private Condition condition;
    private List<KilometerState> kilometerStateList=new ArrayList<>();

    public void addKilometerCount(int kilometerCount){
        kilometerStateList.add(new KilometerState(kilometerCount, LocalDate.now()));
    }
}
