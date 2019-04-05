package ru.tsypaev.database.backend.dataretrieval.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tsypaev.database.backend.dataretrieval.entity.Movie;
import ru.tsypaev.database.backend.dataretrieval.services.LuceneService;

import java.util.List;

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
    List<Movie> getMovies(@RequestParam("q") String text) throws Exception {
        return luceneService.searchLucene(text);
    }
}
