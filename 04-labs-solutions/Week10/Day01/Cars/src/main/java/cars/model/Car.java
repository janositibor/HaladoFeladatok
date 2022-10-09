package cars.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String type;
    private int age;
    @Column(name="car_condition")
    @Enumerated(EnumType.STRING)
    private Condition condition;
    @ElementCollection
    private List<KilometerState> kilometerStateList=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="seller_id")
    private CarSeller seller;

    public void addKilometerCount(int kilometerCount){
        kilometerStateList.add(new KilometerState(kilometerCount, LocalDate.now()));
    }
}
