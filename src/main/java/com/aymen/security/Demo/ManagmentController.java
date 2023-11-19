package com.aymen.security.Demo;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/managment")
public class ManagmentController {
        @GetMapping
    public String get(){
        return "GET:: managment controller";
    }
    @PostMapping
    public String post(){
        return "POST:: managment controller";
    }
    @PutMapping
    public String put(){
        return "PUT:: managment controller";
    }
    @DeleteMapping
    public String delete(){
        return "DELETE:: managment controller";
    }
}
