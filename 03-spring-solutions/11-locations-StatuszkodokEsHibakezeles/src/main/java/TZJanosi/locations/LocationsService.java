package TZJanosi.locations;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.internal.bytebuddy.description.method.MethodDescription;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LocationsService {
    private ModelMapper modelMapper;
    private Type targetListType=new TypeToken<List<LocationDto>>(){}.getType();
    private AtomicLong idGenerator=new AtomicLong();
    private List<Location> locations= Collections.synchronizedList(new ArrayList<>(List.of(new Location(idGenerator.incrementAndGet(),"Otthon",45.1,85.3), new Location(idGenerator.incrementAndGet(),"Munkahely",46.2,75.4))));

    public LocationsService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<LocationDto> getLocations(){

        return modelMapper.map(locations,targetListType);
    }

    public List<LocationDto> getLocationsContains(Optional<String> substring) {
        List<Location> filteredList=locations.stream().filter(l->(substring.isEmpty() || l.getName().toLowerCase().contains(substring.get().toLowerCase()))).toList();
        return modelMapper.map(filteredList,targetListType);
    }

    public LocationDto getLocationById(long id) {
        Location location=locations.stream()
                .filter(l->l.getId()==id)
                .findFirst()
                .orElseThrow(() -> new LocationNotFoundException("No location with ID: "+id));
        return modelMapper.map(location,LocationDto.class);
    }

    public LocationDto createLocation(CreateLocationCommand createLocationCommand) {
        System.out.println(createLocationCommand.getName()+" - "+createLocationCommand.getLat()+" - "+createLocationCommand.getLon());
        Location location=new Location(idGenerator.incrementAndGet(), createLocationCommand.getName(), createLocationCommand.getLat(), createLocationCommand.getLon());
        locations.add(location);
        return modelMapper.map(location,LocationDto.class);
    }

    public LocationDto updateLocation(long id, UpdateLocationCommand updateLocationCommand) {
        Location location=locations.stream()
                .filter(l->l.getId()==id)
                .findFirst().orElseThrow(() -> new LocationNotFoundException("No location with ID: "+id));
        location.setName(updateLocationCommand.getName());
        location.setLat(updateLocationCommand.getLat());
        location.setLon(updateLocationCommand.getLon());
        return modelMapper.map(location,LocationDto.class);
    }

    public void deleteLocation(long id) {
        Location location=locations.stream()
                .filter(l->l.getId()==id)
                .findFirst().orElseThrow(() -> new LocationNotFoundException("No location with ID: "+id));
        locations.remove(location);
    }
}
