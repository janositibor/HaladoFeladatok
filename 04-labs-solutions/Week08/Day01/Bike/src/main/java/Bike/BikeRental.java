package Bike;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class BikeRental {
    private Long id;
    private String bikeID;
    private String lastUserID;
    private LocalDateTime lastRentFinished;
    private double lastDistance;
}
