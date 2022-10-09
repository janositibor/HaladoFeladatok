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
        System.out.println(applicationContext.getBean("englishGreetingService"));
        System.out.println(((GreetingController) applicationContext.getBean("greetingController")).getService());
        System.out.println(((GreetingController) applicationContext.getBean("greetingController")).sayhello());
//        System.out.println(((GreetingController) applicationContext.getBean("greetingController")).getName());
    }

//    @Bean
//    String createName(){
//        return "this is a BEAN";
//    }
//    @Bean
//    String createOtherName(){
//        return "this is another BEAN";
//    }
}
