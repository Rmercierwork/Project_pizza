package fr.epsi.rennes.poec.raphael.pizza;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "fr.epsi.rennes.poec.raphael.pizza")
public class PizzaApplication {

    public static void main(String[] args) {

        SpringApplication.run(PizzaApplication.class, args);
    }
}