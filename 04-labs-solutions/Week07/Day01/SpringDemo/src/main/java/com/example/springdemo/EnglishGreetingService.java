package com.example.springdemo;

import org.springframework.stereotype.Service;

@Service
public class EnglishGreetingService implements GreetingService{
    @Override
    public String sayHello() {
        return "Hello World";
    }
}
