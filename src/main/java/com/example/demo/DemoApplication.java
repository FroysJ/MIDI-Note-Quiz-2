package com.example.demo;
import javafx.stage.Stage;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web.MidiApp;

@SpringBootApplication
@RestController
public class DemoApplication {

    public static boolean toClose = false;

    private static ConfigurableBootstrapContext context;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    public static void stopServer() {
        if (context != null) {
            SpringApplication.exit((ApplicationContext) context, () -> 0);
        } else {
            System.out.println("No Spring Application Context");
        }
    }
}