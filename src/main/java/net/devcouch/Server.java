package net.devcouch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("net.devcouch")
public class Server {

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }
}
