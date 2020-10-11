package com.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @RequestMapping("/userfallback")
    public Mono<String> userService(){
        return Mono.just("User service is taking too long to respond or is down, Please try again later");
    }

    @RequestMapping("/notefallback")
    public Mono<String> noteService(){
        return Mono.just("Note service is taking too long to respond or is down, Please try again later");
    }

}
