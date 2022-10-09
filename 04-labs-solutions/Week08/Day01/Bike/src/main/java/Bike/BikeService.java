package Bike;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class BikeService {
    private AtomicLong idGenerator= new AtomicLong();
    private List<BikeRental> bikeRentalsList;
    private ModelMapper modelMapper;

    public BikeService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

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
            bikeRentalsList.add(new BikeRental(idGenerator.incrementAndGet(),bike,user,lastUsed,distance));
        }
    }





//    public List<BikeRentalDTO> getAllRentals() {
//        checkAndLoadIfNeeded();
//        BikeRentalDTO temp;
//        List<BikeRentalDTO> result=new ArrayList<>();
//        for (BikeRental bikeRental:bikeRentalsList){
//            temp=convertBikeRental2BikeRentalDTO(bikeRental);
//            result.add(temp);
//        }
//        return result;
//    }
public List<BikeRentalDTO> getAllRentals() {
        checkAndLoadIfNeeded();
        return bikeRentalsList.stream()
                .map(r->modelMapper.map(r,BikeRentalDTO.class))
                .collect(Collectors.toList());
    }



    public Set<String> getUserIDs() {
        checkAndLoadIfNeeded();
        return bikeRentalsList.stream()
                .map(BikeRental::getLastUserID)
                .collect(Collectors.toSet());
    }




//    public BikeRentalDTO getRentalByID(int id) {
//        checkAndLoadIfNeeded();
//        return bikeRentalsList.stream()
//                .filter(r->r.getRentalID()==id)
//                .map(r->convertBikeRental2BikeRentalDTO(r))
//                .findFirst().orElseThrow(()->new IllegalArgumentException("No rental with id: "+id));
//
//    }

    public BikeRentalDTO getRentalByID(int id) {
        checkAndLoadIfNeeded();
        return bikeRentalsList.stream()
                .filter(r->r.getId()==id)
                .map(r->modelMapper.map(r,BikeRentalDTO.class))
                .findFirst().orElseThrow(()->new IllegalArgumentException("No rental with id: "+id));

    }

    public List<BikeRentalDTO> getRentalsFrom(Optional<LocalDateTime> from) {
        checkAndLoadIfNeeded();
        if(from.isPresent()){
            return bikeRentalsList.stream()
                    .filter(r->!r.getLastRentFinished().isBefore(from.get()))
                    .map(r->convertBikeRental2BikeRentalDTO(r))
                    .collect(Collectors.toList());
        }
        else{
            return getAllRentals();
        }


    }

    public BikeRentalDTO createRental(CreateBikeRentalCommand createBikeRentalCommand) {
        checkAndLoadIfNeeded();
        BikeRental bikeRental=modelMapper.map(createBikeRentalCommand,BikeRental.class);
        bikeRental.setId(idGenerator.incrementAndGet());
        bikeRentalsList.add(bikeRental);
        return modelMapper.map(bikeRental,BikeRentalDTO.class);
    }

    private void checkAndLoadIfNeeded() {
        if(bikeRentalsList==null){
            bikeRentalsList=new ArrayList<>();
            readFromFile(Paths.get("src/main/resources/bikes.csv"));
        }
    }

    private BikeRentalDTO convertBikeRental2BikeRentalDTO(BikeRental bikeRental) {
        return new BikeRentalDTO(bikeRental.getId(),bikeRental.getBikeID(),bikeRental.getLastUserID(),bikeRental.getLastRentFinished(),bikeRental.getLastDistance());
    }
}
