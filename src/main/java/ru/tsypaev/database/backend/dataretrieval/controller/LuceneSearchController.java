package ru.tsypaev.database.backend.dataretrieval.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tsypaev.database.backend.dataretrieval.entity.Movie;
import ru.tsypaev.database.backend.dataretrieval.services.lucene.LuceneService;

import javax.servlet.http.HttpServletResponse;
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

    @GetMapping(params = {"q", "type"})
    List<Movie> getMovies(@RequestParam("q") String text,  @RequestParam("type") String type, HttpServletResponse response) throws Exception {
            response.addHeader("Access-Control-Allow-Origin","*");
            return luceneService.searchLucene(text, type);
    }
}
