package com.example.springdemo;

import org.springframework.stereotype.Service;

@Service
public class HungarianGreetingService implements GreetingService{
    @Override
    public String sayHello() {
        return "Helló Virág!";
    }
}
