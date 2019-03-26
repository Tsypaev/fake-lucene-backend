package ru.tsypaev.database.backend.dataretrieval.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tsypaev Vladimir
 */

@RestController
@RequestMapping("/ping")
public class PingController {

    @GetMapping
    HttpStatus getPing() {
        return HttpStatus.OK;
    }
}
