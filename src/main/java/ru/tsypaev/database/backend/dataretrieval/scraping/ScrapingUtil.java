package ru.tsypaev.database.backend.dataretrieval.scraping;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.tsypaev.database.backend.dataretrieval.entity.MoveInfo;
import ru.tsypaev.database.backend.dataretrieval.repository.MoviesRepository;

import java.io.IOException;
import java.util.List;

/**
 * @author Vladimir Tsypaev
 */

@Service
public class ScrapingUtil {

    public MoveInfo createMovie(String s, Document document) throws IOException {
        String title = WebSpider.getName(document);
        String year = WebSpider.getYear(document);
        String premierData = WebSpider.getPremierData(document);
        String genresList = WebSpider.getGenresList(document);
        String director = WebSpider.getDirector(document);
        String starsList = WebSpider.getStarsList(document);
        String annotation = WebSpider.getAnnotation(document);
        String synopsis = WebSpider.getSynopsis(document, s);

        MoveInfo movieInfo = new MoveInfo(
                title,
                year,
                premierData,
                genresList,
                director,
                starsList,
                annotation,
                synopsis
        );
        return movieInfo;
    }
}
