package com.galactus.thread.api.rest;

import com.galactus.thread.application.ThreadService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/thread")
public class ThreadRestController {
    private final ThreadService service;

    public ThreadRestController(ThreadService service) {
        this.service = service;
    }
}
