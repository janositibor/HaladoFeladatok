package TZJanosi.locations;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class LocationsService {
    private ModelMapper modelMapper;
    private LocationRepository repository;

    private Type targetListType=new TypeToken<List<LocationDto>>(){}.getType();
    private AtomicLong idGenerator=new AtomicLong();
    private List<Location> locations= Collections.synchronizedList(new ArrayList<>(List.of(new Location(idGenerator.incrementAndGet(),"Otthon",45.1,85.3), new Location(idGenerator.incrementAndGet(),"Munkahely",46.2,75.4))));

    public LocationsService(ModelMapper modelMapper, LocationRepository repository) {
        this.modelMapper = modelMapper;
        this.repository = repository;
    }

    public List<LocationDto> getLocations(){
        return repository.findAll().stream()
                .map(l->modelMapper.map(l,LocationDto.class))
                .collect(Collectors.toList());

    }

    public List<LocationDto> getLocationsContains(Optional<String> substring) {
        List<Location> filteredList=locations.stream().filter(l->(substring.isEmpty() || l.getName().toLowerCase().contains(substring.get().toLowerCase()))).toList();
        return modelMapper.map(filteredList,targetListType);
    }

    public LocationDto getLocationById(long id) {
        return modelMapper.map(repository.findById(id).orElseThrow(()->new IllegalArgumentException("No location with this ID: "+id)),LocationDto.class);
    }

    public LocationDto createLocation(CreateLocationCommand createLocationCommand) {
        System.out.println(createLocationCommand.getName()+" - "+createLocationCommand.getLat()+" - "+createLocationCommand.getLon());
        Location location=new Location(createLocationCommand.getName(), createLocationCommand.getLat(), createLocationCommand.getLon());
        repository.save(location);
        return modelMapper.map(location,LocationDto.class);
    }

    @Transactional
    public LocationDto updateLocation(long id, UpdateLocationCommand updateLocationCommand) {
        Location location=repository.findById(id).orElseThrow(()->new IllegalArgumentException("No location with this ID: "+id));
        location.setName(updateLocationCommand.getName());
        location.setLat(updateLocationCommand.getLat());
        location.setLon(updateLocationCommand.getLon());
        return modelMapper.map(location,LocationDto.class);
    }

    public void deleteLocation(long id) {
        repository.deleteById(id);
    }
    public void deleteAllLocation() {
        repository.deleteAll();
    }
}
