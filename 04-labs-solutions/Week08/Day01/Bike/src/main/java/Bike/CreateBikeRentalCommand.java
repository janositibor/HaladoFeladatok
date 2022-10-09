package Bike;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBikeRentalCommand {
    private String bikeID;
    private String lastUserID;
    private LocalDateTime lastRentFinished;
    private double lastDistance;
}
