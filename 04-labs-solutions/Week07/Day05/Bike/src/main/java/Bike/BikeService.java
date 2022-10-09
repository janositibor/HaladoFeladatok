package Bike;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BikeService {
    private List<BikeRental> bikeRentalsList;
    public void readFromFile(Path path){
        //Paths.get("src/main/resources/bikes.csv")
        List<String> lines;
        try {
            lines= Files.readAllLines(path);
        } catch (IOException ioe) {
            throw new IllegalStateException("Can not read file: "+path, ioe);
//            throw new IllegalStateException("Can not read file.", ioe);
        }
        process(lines);
    }
    private void process(List<String> lines){
        String[] fieldsArray;
        String bike;
        String user;
        LocalDateTime lastUsed;
        double distance;
        for(String line:lines){
            fieldsArray=line.split(";");
            bike=fieldsArray[0];
            user=fieldsArray[1];
            lastUsed=LocalDateTime.parse(fieldsArray[2]);
            distance=Double.parseDouble(fieldsArray[3]);
            bikeRentalsList.add(new BikeRental(bike,user,lastUsed,distance));
        }
    }





    public List<BikeRental> getAllRentals() {
        checkAndLoadIfNeeded();
        return bikeRentalsList;

    }
    public Set<String> getUserIDs() {
        checkAndLoadIfNeeded();
        return bikeRentalsList.stream()
                .map(BikeRental::getLastUserID)
                .collect(Collectors.toSet());
    }

    private void checkAndLoadIfNeeded() {
        if(bikeRentalsList==null){
            bikeRentalsList=new ArrayList<>();
            readFromFile(Paths.get("src/main/resources/bikes.csv"));
        }
    }


}
