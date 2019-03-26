package ru.tsypaev.database.backend.dataretrieval.controller;

import org.springframework.web.bind.annotation.*;
import ru.tsypaev.database.backend.dataretrieval.entity.Movie;
import ru.tsypaev.database.backend.dataretrieval.services.MoviesService;
import ru.tsypaev.database.backend.dataretrieval.services.TextProcessingService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private MoviesService moviesService;
    private TextProcessingService textProcessingService;

    SearchController(MoviesService moviesService, TextProcessingService textProcessingService){
        this.moviesService = moviesService;
        this.textProcessingService = textProcessingService;
    }

    @GetMapping(params = {"q"})
    List<Movie> getMoviesCount(@RequestParam("q") String searchingText, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin","*");
        int yearFromText = textProcessingService.getYearFromText(searchingText);
        return moviesService.findText("%" + searchingText + "%",yearFromText);
    }
}
