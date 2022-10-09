package birds;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BirdService {
    private BirdDao birdDao;

    public BirdService(BirdDao birdDao) {
        this.birdDao = birdDao;
    }

    public Map<BirdSpecies, Integer> getBirdStatistics(){
        List<Bird> birdsList = birdDao.listBirds();
        Map<BirdSpecies, Integer> output=new HashMap<>();
        for(Bird bird:birdsList){
            BirdSpecies birdSpecies=bird.getBirdSpecies();
            output.putIfAbsent(birdSpecies, 0);
            output.put(birdSpecies, output.get(birdSpecies) + 1);
        }
        return output;
    }
}
