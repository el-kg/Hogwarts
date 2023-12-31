package ru.hogwarts.school.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;
import java.util.stream.Stream;

@RestController
public class InfoController {
    Logger logger = (Logger) LoggerFactory.getLogger(InfoController.class);
    @Value("${server.port}")
    private int serverPort;
    @GetMapping("/port")
    public int getServerPortNumber(){return serverPort;}
    @GetMapping("/sum41")
    public int getSum(){
        long time =System.currentTimeMillis();
        Stream.iterate(1,a->a+1)
                .limit(1_000_000)
                //.parallel()
                .reduce(0,(a,b)->a+b);
        time=System.currentTimeMillis()-time;
        System.out.println("Время работы ="+time);
        return (int)time;
    }
}
