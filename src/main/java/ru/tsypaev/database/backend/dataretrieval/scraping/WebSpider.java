package ru.tsypaev.database.backend.dataretrieval.scraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author Vladimir Tsypaev
 */
public class WebSpider {

    public static String getName(Document document) {
        Elements elements = document.select(".title_wrapper>h1");
        return elements.text().replaceAll(" \\(.*\\)", "");
    }

    public static String getYear(Document document) {
        Elements elements = document.select(".title_wrapper>h1>span>a");
        return String.valueOf(Integer.parseInt(elements.text()));
    }

    public static String getPremierData(Document document) {
        Elements elements = document.select("#title-overview-widget > div.vital > div.title_block > div > div.titleBar > div.title_wrapper > div.subtext > a:last-child");
        return elements.text().replaceAll("\\(.*\\)", "");
    }

    public static String getGenresList(Document document) {
        Elements elements = document.select("#titleStoryLine > div:nth-child(10) > a");
        StringBuilder str = new StringBuilder();
        return workWithStringBuilder(elements, str);
    }

    public static String getDirector(Document document) {
        Elements elements = document.select("#title-overview-widget > div.plot_summary_wrapper > div.plot_summary > div:nth-child(2) > a");
        return elements.text();
    }

    public static String getStarsList(Document document) {
        Elements elements = document.select("#title-overview-widget > div.plot_summary_wrapper > div.plot_summary > div:nth-child(4) > a:not(:last-child)");
        StringBuilder str = new StringBuilder();
        return workWithStringBuilder(elements, str);
    }

    public static String getAnnotation(Document document) {
        Elements elements = document.select("#titleStoryLine > div:nth-child(3) > p > span");
        return elements.text();
    }

    public static String getSynopsis(Document document, String id) throws IOException {
        document = Jsoup.connect("https://www.imdb.com/title/tt" + id + "/synopsis?ref_=tt_stry_pl").get();
        Elements elements = document.select("#plot-synopsis-content>li");
        return elements.text();
    }


    private static String workWithStringBuilder(Elements elements, StringBuilder str) {
        for (Element element : elements) {
            str.insert(str.length(), element.text() + " ");
        }
        return str.toString();
    }
}
