package ru.tsypaev.database.backend.dataretrieval.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tsypaev.database.backend.dataretrieval.entity.Movie;
import ru.tsypaev.database.backend.dataretrieval.services.MoviesService;
import ru.tsypaev.database.backend.dataretrieval.services.TextUtilService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Class for searching movies info via database query.
 * @author Tsypaev Vladimir
 */

@RestController
@RequestMapping("/search")
public class SearchController {

    private MoviesService moviesService;
    private TextUtilService textUtilService;

    SearchController(MoviesService moviesService, TextUtilService textUtilService){
        this.moviesService = moviesService;
        this.textUtilService = textUtilService;
    }

    /**
     * @param text - searching text(part of film name)
     * @param type - type of searching(may be 'all' or 'year')
     * @param response - HTTP response
     * @return movies in JSON format
     */
    @GetMapping(params = {"q", "type"})
    List<Movie> getMoviesCount(@RequestParam("q") String text, @RequestParam("type") String type, HttpServletResponse response) {
        if(type.equals("all")){
            response.addHeader("Access-Control-Allow-Origin","*");
            return moviesService.findText("%" + text + "%");
        }
        else if (type.equals("year")){
            response.addHeader("Access-Control-Allow-Origin","*");
            int yearFromText = textUtilService.getYearFromText(text);
            String processingString = textUtilService.deleteYearFromText(text, yearFromText);
            return moviesService.findText("%" + processingString + "%", yearFromText);
        }
        return null;
    }


}
