package ru.tsypaev.database.backend.dataretrieval.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tsypaev.database.backend.dataretrieval.entity.Movie;
import ru.tsypaev.database.backend.dataretrieval.services.MoviesService;
import ru.tsypaev.database.backend.dataretrieval.services.TextProcessingService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Tsypaev Vladimir
 */

@RestController
@RequestMapping("/search")
public class SearchController {

    private MoviesService moviesService;
    private TextProcessingService textProcessingService;

    SearchController(MoviesService moviesService, TextProcessingService textProcessingService){
        this.moviesService = moviesService;
        this.textProcessingService = textProcessingService;
    }

    @GetMapping(params = {"q", "type"})
    List<Movie> getMoviesCount(@RequestParam("q") String searchingText, @RequestParam("type") String type, HttpServletResponse response) {
        if(type.equals("all")){
            response.addHeader("Access-Control-Allow-Origin","*");
            return moviesService.findText("%" + searchingText + "%");
        }
        else if (type.equals("year")){
            response.addHeader("Access-Control-Allow-Origin","*");
            int yearFromText = textProcessingService.getYearFromText(searchingText);
            String processingString = textProcessingService.deleteYearFromText(searchingText, yearFromText);
            return moviesService.findText("%" + processingString + "%", yearFromText);
        }
        return null;
    }


}
