package com.example.springdemo;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

//@Profile("HU")
@Service
public class HungarianGreetingService implements GreetingService{
    @Override
    public String sayHello() {
        return "Helló Virág!";
    }
}
