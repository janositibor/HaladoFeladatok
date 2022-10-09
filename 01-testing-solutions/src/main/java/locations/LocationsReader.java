package locations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LocationsReader {
    List<Location> locationList=new ArrayList<>();
    LocationParser locationParser=new LocationParser();

    public List<Location> filterLocationsBeyondArcticCircle(List<Location> locationsToFilter){
        return locationsToFilter.stream()
                .filter(l->l.getLatitude()>66.57)
                .toList();
    }

    public void readDataFromFile(Path path){
        //Paths.get("src/test/resources/moviesintheaters.txt")
        List<String> lines;
        try {
            lines= Files.readAllLines(path);
        } catch (IOException ioe) {
            throw new IllegalStateException("Can not read file: "+path, ioe);
        }
        process(lines);
    }
    private void process(List<String> lines){
        for(int i=0;i< lines.size();i++){
            locationList.add(locationParser.parse(lines.get(i)));
        }
    }

    public List<Location> getLocationList() {
        return locationList;
    }
}
