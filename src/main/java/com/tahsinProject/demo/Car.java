package com.tahsinProject.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Car {
    @Autowired
    private Engine engine;

    @GetMapping("/ok")
    public String ok(){
        return engine.start();
//        System.out.println("Car is running");
    }



}
