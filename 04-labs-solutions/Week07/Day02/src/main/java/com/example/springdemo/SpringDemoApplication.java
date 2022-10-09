package com.example.springdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringDemoApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext=SpringApplication.run(SpringDemoApplication.class, args);
//        GreetingController greetingController= new GreetingController();
        System.out.println(applicationContext.getBeansOfType(GreetingController.class).size());
        System.out.println(applicationContext.getBeansOfType(EnglishGreetingService.class).size());
        System.out.println(((GreetingController) applicationContext.getBean("greetingController")).getGreetingServiceMap().entrySet());
        System.out.println(((GreetingController) applicationContext.getBean("greetingController")).sayhello("hungarianGreetingService"));
        System.out.println(((GreetingController) applicationContext.getBean("greetingController")).sayhello("englishGreetingService"));
//        System.out.println(((GreetingController) applicationContext.getBean("greetingController")).getName());
    }

//    @Bean
//    public String createName(){
//        return "this is a BEAN";
//    }
//    @Bean
//    public String createOtherName(){
//        return "this is another BEAN";
//    }
}
