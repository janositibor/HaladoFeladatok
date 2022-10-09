package locations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocationsRepository {
    private List<Location>locationsList=new ArrayList<>();

    public void saveLocation(Location locationToSave){
        locationsList.add(locationToSave);
    }

    public Optional<Location> findByName(String name){
        return locationsList.stream()
                .filter(l->l.getName().equals(name))
                .findFirst();
    }
}
