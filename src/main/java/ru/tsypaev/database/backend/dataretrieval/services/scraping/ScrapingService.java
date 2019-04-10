package ru.tsypaev.database.backend.dataretrieval.services.scraping;

import org.jsoup.Jsoup;
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
    private ScrapingUtil scrapingUtil;

    public ScrapingService(MoviesRepository moviesRepository, ScrapingUtil scrapingUtil) {
        this.moviesRepository = moviesRepository;
        this.scrapingUtil = scrapingUtil;
    }


    public void updateDatabase() throws IOException {
        List<Integer> ids = moviesRepository.getId();
        for (Integer id : ids) {
            String s = TextProcessingService.addZeros(id);

            Document document = Jsoup.connect("https://www.imdb.com/title/tt" + s + "/").get();

            scrapingUtil.putInfoIntoDb(s, document);
        }
    }
}
