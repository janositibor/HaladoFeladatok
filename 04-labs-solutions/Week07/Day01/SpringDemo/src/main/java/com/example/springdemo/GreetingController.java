package com.example.springdemo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {
//    @Autowired
    private GreetingService service;
//    private String name;

    public GreetingController(@Qualifier("hungarianGreetingService") GreetingService service) {
        this.service = service;
    }

    public String sayhello(){
        return service.sayHello();
    }

//    public GreetingController(GreetingService service, String name) {
//        this.service = service;
//        this.name = name;
//    }

    public GreetingService getService() {
        return service;
    }

//    public String getName() {
//        return name;
//    }

    //    @Autowired
//    public void setService(GreetingService service) {
//        this.service = service;
//    }
}
