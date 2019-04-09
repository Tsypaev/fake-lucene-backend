package ru.tsypaev.database.backend.dataretrieval.scraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Tsypaev
 */
public class WebSpider {

    private static String getName(Document document){
        Elements elements = document.select(".title_wrapper>h1");
        return elements.text().replaceAll(" \\(.*\\)","");
    }

    private static int getYear(Document document){
        Elements elements = document.select(".title_wrapper>h1>span>a");
        return Integer.parseInt(elements.text());
    }

    private static String getPremierData(Document document){
        Elements elements = document.select("#title-overview-widget > div.vital > div.title_block > div > div.titleBar > div.title_wrapper > div.subtext > a:nth-child(8)");
        return elements.text().replaceAll("\\(.*\\)","");
    }

    private static List<String> getGenresList(Document document){
        List<String> list = new ArrayList<String>();
        Elements elements = document.select("#titleStoryLine > div:nth-child(10) > a");
        for(Element element : elements) {
            list.add(element.text());
        }
        return list;
    }

    private static String getDirector(Document document){
        Elements elements = document.select("#title-overview-widget > div.plot_summary_wrapper > div.plot_summary > div:nth-child(2) > a");
        return elements.text();
    }

    private static List<String> getStarsList(Document document){
        List<String> starsList = new ArrayList<String>();
        Elements elements = document.select("#title-overview-widget > div.plot_summary_wrapper > div.plot_summary > div:nth-child(4) > a:nth-child(-n+1)");
        for(Element element : elements) {
            starsList.add(element.text());
        }
        return starsList;
    }

    private static String getAnnotation(Document document){
        Elements elements = document.select("#titleStoryLine > div:nth-child(3) > p > span");
        return elements.text();
    }

    private static String getSynopsis(Document document) throws IOException {
        document = Jsoup.connect("https://www.imdb.com/title/tt0113243/synopsis?ref_=tt_stry_pl").get();
        Elements elements = document.select("#plot-synopsis-content>li");
        return elements.text();
    }

}
