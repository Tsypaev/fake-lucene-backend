package ru.tsypaev.database.backend.dataretrieval.scraping;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.tsypaev.database.backend.dataretrieval.repository.MoviesRepository;

import java.io.IOException;

/**
 * @author Vladimir Tsypaev
 */

@Deprecated
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ScrapingUtil {

    private MoviesRepository moviesRepository;

    public ScrapingUtil(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }


    public void putInfoIntoDb(String s, Document document) throws IOException {
        String premierData = WebSpider.getPremierData(document);
        String genresList = WebSpider.getGenresList(document);
        String director = WebSpider.getDirector(document);
        String starsList = WebSpider.getStarsList(document);
        String annotation = WebSpider.getAnnotation(document);
        String synopsis = WebSpider.getSynopsis(document, s);


        moviesRepository.setMoviesInfo(premierData, genresList, director, starsList, annotation, synopsis, Integer.valueOf(s));
    }
}
