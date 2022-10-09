package TZJanosi.locations;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.internal.bytebuddy.description.method.MethodDescription;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;

@Service
public class LocationsService {
    private ModelMapper modelMapper;
    private Type targetListType=new TypeToken<List<LocationDto>>(){}.getType();
    private List<Location> locations= Collections.synchronizedList(new ArrayList<>(List.of(new Location(1L,"Otthon",45.1,85.3), new Location(2L,"Munkahely",46.2,75.4))));

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
        Location location=locations.stream().filter(l->l.getId()==id).findFirst().get();
        return modelMapper.map(location,LocationDto.class);
    }
}
