package cars;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

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
