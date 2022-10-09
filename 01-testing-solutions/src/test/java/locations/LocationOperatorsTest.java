package locations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class LocationOperatorsTest {
    LocationOperators locationOperators=new LocationOperators();
    List<Location> locations=new ArrayList<>();

    @BeforeEach
    void init(){
        locations.add(new Location("Perth",-31.953512,115.857048));
        locations.add(new Location("Jyvaskyla",62.24147,25.72088));
        locations.add(new Location("Geneva",46.20222,6.14569));
    }

    @Test
    @DisplayName("NorthernHemisphereTest")
    void testNorthLocations(){
        assertEquals(List.of("Jyvaskyla", "Geneva"),locationOperators.filterOnNorth(locations).stream()
                .map(Location::getName)
                .collect(Collectors.toList()));
    }
}