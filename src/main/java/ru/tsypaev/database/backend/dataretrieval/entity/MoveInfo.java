package ru.tsypaev.database.backend.dataretrieval.entity;

import java.util.List;

/**
 * @author Vladimir Tsypaev
 */
@Deprecated
public class MoveInfo {

    private String title;
    private int year;
    private String primierData;
    private List<String> genresList;
    private String director;
    private List<String> filmStars;
    private String annotation;
    private String synopsis;

    MoveInfo(String title, int year, String primierData, List<String> genresList, String director, List<String> filmStars, String annotation, String synopsis) {
        this.title = title;
        this.year = year;
        this.primierData = primierData;
        this.genresList = genresList;
        this.director = director;
        this.filmStars = filmStars;
        this.annotation = annotation;
        this.synopsis = synopsis;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPrimierData() {
        return primierData;
    }

    public void setPrimierData(String primierData) {
        this.primierData = primierData;
    }

    public List<String> getGenresList() {
        return genresList;
    }

    public void setGenresList(List<String> genresList) {
        this.genresList = genresList;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public List<String> getFilmStars() {
        return filmStars;
    }

    public void setFilmStars(List<String> filmStars) {
        this.filmStars = filmStars;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

}
