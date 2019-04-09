package ru.tsypaev.database.backend.dataretrieval.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tsypaev.database.backend.dataretrieval.services.scraping.ScrapingService;

import java.io.IOException;

/**
 * @author Vladimir Tsypaev
 */

@RestController
@RequestMapping("/update")
public class UpdateDatabaseController {

    private ScrapingService scrapingService;

    public UpdateDatabaseController(ScrapingService scrapingService) {
        this.scrapingService = scrapingService;
    }

    @GetMapping
    void updateDatabase() throws IOException {
        scrapingService.updateDatabase();
    }
}
