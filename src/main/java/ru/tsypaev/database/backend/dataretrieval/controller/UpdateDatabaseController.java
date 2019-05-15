package ru.tsypaev.database.backend.dataretrieval.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tsypaev.database.backend.dataretrieval.entity.MoveInfo;
import ru.tsypaev.database.backend.dataretrieval.services.scraping.ScrapingService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Vladimir Tsypaev
 */

@RestController
@RequestMapping("/getMovie")
public class UpdateDatabaseController {

    private ScrapingService scrapingService;

    public UpdateDatabaseController(ScrapingService scrapingService) {
        this.scrapingService = scrapingService;
    }

    @GetMapping(params = {"id"})
    MoveInfo getMovie(@RequestParam("id") String id, HttpServletResponse response) throws IOException {
        response.addHeader("Access-Control-Allow-Origin","*");
        int realId = Integer.valueOf(id);
        if (realId > 10000000){
            return null;
        }
        return scrapingService.getMovie(realId);
    }
}
