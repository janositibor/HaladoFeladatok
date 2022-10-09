package Incrementer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class IncrementerController {
    private IncrementerService incrementerService;


    public IncrementerController(IncrementerService incrementerService) {
        this.incrementerService = incrementerService;
    }

    @GetMapping("/")
    public String getCount(){
        incrementerService.increment();
        return incrementerService.getCounter()+ "<br><br>Generated: "+ LocalDateTime.now();

    }
}
