package locations;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Controller
public class LocationsController {
    private List<Location> locations= Arrays.asList(new Location(1L,"Otthon",45.1,85.3), new Location(2L,"Munkahely",46.2,75.4));

    @GetMapping("/")
    @ResponseBody
    public String getLocations(){
        return locations.toString() + "<br>Generated: "+ LocalDateTime.now();
    }
}
