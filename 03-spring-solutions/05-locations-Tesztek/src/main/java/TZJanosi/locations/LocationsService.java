package TZJanosi.locations;

import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class LocationsService {
    private List<Location> locations= Arrays.asList(new Location(1L,"Otthon",45.1,85.3), new Location(2L,"Munkahely",46.2,75.4));

    public List<Location> getLocations(){
        return locations;
    }
}
