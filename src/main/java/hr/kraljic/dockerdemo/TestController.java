package hr.kraljic.dockerdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/")
    public String hello() {
        return "Hello world!! New version v2 :: " + DockerDemoApplication.number;
    }
}
