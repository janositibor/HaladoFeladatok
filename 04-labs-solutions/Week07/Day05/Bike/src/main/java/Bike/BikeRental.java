package Bike;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class BikeRental {
    private String ID;
    private String lastUserID;
    private LocalDateTime lastRentFinished;
    private double lastDistance;
}
