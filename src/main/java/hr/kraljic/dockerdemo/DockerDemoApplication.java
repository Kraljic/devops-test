package hr.kraljic.dockerdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;

@SpringBootApplication
public class DockerDemoApplication {
    public static Integer number = 0;

    public static void main(String[] args) {
        number = new Random().nextInt();

        SpringApplication.run(DockerDemoApplication.class, args);
    }

}
