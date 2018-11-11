package com.divanshu.reap.finalProject.events;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap {

    @EventListener(ApplicationStartedEvent.class)
    public void init() {

        System.out.println("Application started..");
    }

}
