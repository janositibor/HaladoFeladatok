package locations;

import java.util.Optional;

public class LocationsService {
    private LocationsRepository locationsRepository;

    public LocationsService(LocationsRepository locationsRepository) {
        this.locationsRepository = locationsRepository;
    }

    public Optional<Double> calculateDistance(String name1, String name2){
        Optional<Location> optionalLocation1=locationsRepository.findByName(name1);
        Optional<Location> optionalLocation2=locationsRepository.findByName(name2);

        if(optionalLocation1.isEmpty()){
            return Optional.empty();
        }
        if(optionalLocation2.isEmpty()){
            return Optional.empty();
        }

        Location location1=optionalLocation1.get();
        Location location2=optionalLocation2.get();
        return Optional.of(location1.distanceFrom(location2));
    }
}
