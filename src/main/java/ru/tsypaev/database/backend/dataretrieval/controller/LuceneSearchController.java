package ru.tsypaev.database.backend.dataretrieval.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tsypaev.database.backend.dataretrieval.services.LuceneService;

/**
 * @author Tsypaev Vladimir
 */

@RestController
@RequestMapping("/lucene/search")
public class LuceneSearchController {

    private LuceneService luceneService;

    LuceneSearchController(LuceneService luceneService){
        this.luceneService = luceneService;
    }

    @GetMapping(params = {"q"})
    String getMoviesCount(@RequestParam("q") String searchingText) throws Exception {
        return luceneService.searchLucene(searchingText);
    }
}
