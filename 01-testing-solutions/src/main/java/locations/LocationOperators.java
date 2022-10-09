package locations;

import java.util.List;

public class LocationOperators {
    public List<Location> filterOnNorth(List<Location> locationsList){
        return locationsList.stream()
                .filter(l->l.getLatitude()>0)
                .toList();
    }
}
