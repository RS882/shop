package de.aittr.g_38_jp_shop.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Value("${messages.hello}")
    private String hello;

    @GetMapping
    public String hello() {
        return hello;
    }
}
