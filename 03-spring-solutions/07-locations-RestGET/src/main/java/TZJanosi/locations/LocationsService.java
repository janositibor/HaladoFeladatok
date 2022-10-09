package TZJanosi.locations;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.internal.bytebuddy.description.method.MethodDescription;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class LocationsService {
    private ModelMapper modelMapper;
    private List<Location> locations= Collections.synchronizedList(new ArrayList<>(List.of(new Location(1L,"Otthon",45.1,85.3), new Location(2L,"Munkahely",46.2,75.4))));

    public LocationsService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<LocationDto> getLocations(){
        Type targetListType=new TypeToken<List<LocationDto>>(){}.getType();
        return modelMapper.map(locations,targetListType);
    }
}
