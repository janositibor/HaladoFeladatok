package cars.dtos;

import cars.model.Condition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCarCommand {

    @NotBlank
    private String brand;

    @NotBlank
    private String type;
    @PositiveOrZero
    private int age;
    private Condition condition;
    @PositiveOrZero
    private int kmCounter;
}
