package cars.dtos;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class KilometerStateDTO {
    private int kmCounter;
    private LocalDate date;
}
