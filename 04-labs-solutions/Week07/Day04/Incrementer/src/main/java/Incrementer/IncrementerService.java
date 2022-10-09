package Incrementer;

import org.springframework.stereotype.Service;

@Service
public class IncrementerService {
    private int counter=0;

    public IncrementerService() {
    }

    public void increment(){
        counter++;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
