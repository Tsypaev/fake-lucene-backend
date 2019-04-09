package ru.tsypaev.database.backend.dataretrieval.services.scraping;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tsypaev.database.backend.dataretrieval.repository.MoviesRepository;
import ru.tsypaev.database.backend.dataretrieval.scraping.ScrapingUtil;
import ru.tsypaev.database.backend.dataretrieval.services.TextProcessingService;

import java.io.IOException;
import java.util.List;

/**
 * @author Vladimir Tsypaev
 */

@Service
public class ScrapingService {

    @Autowired
    MoviesRepository moviesRepository;
    ScrapingUtil scrapingUtil;

    public ScrapingService(MoviesRepository moviesRepository, ScrapingUtil scrapingUtil) {
        this.moviesRepository = moviesRepository;
        this.scrapingUtil = scrapingUtil;
    }


    public void updateDatabase() throws IOException {
        List<Integer> ids = moviesRepository.getId();
        for (Integer id : ids) {
            String s = TextProcessingService.addZeros(id);

            Document document = new Document("https://www.imdb.com/title/tt" + s + "/");

            scrapingUtil.putInfoIntoDb(s, document);
        }
    }
}
