package com.example.springdemo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GreetingController {
//    @Autowired
//    private GreetingService service;
//    private String name;

    private Map<String, GreetingService> greetingServiceMap;

    public GreetingController(Map<String, GreetingService> greetingServiceMap) {
        this.greetingServiceMap = greetingServiceMap;
    }

    //    public GreetingController(GreetingService service) {
//        this.service = service;
//    }

    public String sayhello(String type){
        GreetingService service=greetingServiceMap.get(type);
        return service.sayHello();
    }

    public Map<String, GreetingService> getGreetingServiceMap() {
        return greetingServiceMap;
    }

    //    public GreetingController(GreetingService service, String name) {
//        this.service = service;
//        this.name = name;
//    }

//    public GreetingService getService() {
//        return service;
//    }

//    public String getName() {
//        return name;
//    }

    //    @Autowired
//    public void setService(GreetingService service) {
//        this.service = service;
//    }
}
