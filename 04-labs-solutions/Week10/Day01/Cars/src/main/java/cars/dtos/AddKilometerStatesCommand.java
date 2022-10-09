package cars.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddKilometerStatesCommand {
    @Positive(message = "The km counter mustn't be negative!")
    private int kmCounter;
}
