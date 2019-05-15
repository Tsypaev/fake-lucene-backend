package ru.tsypaev.database.backend.dataretrieval.entity;

/**
 * @author Vladimir Tsypaev
 */

public class MoveInfo {

    private String title;
    private String year;
    private String primierData;
    private String genresList;//
    private String director;
    private String filmStars;//
    private String annotation;
    private String synopsis;

    public MoveInfo(String title, String year, String primierData, String genresList, String director, String filmStars, String annotation, String synopsis) {
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPrimierData() {
        return primierData;
    }

    public void setPrimierData(String primierData) {
        this.primierData = primierData;
    }

    public String getGenresList() {
        return genresList;
    }

    public void setGenresList(String genresList) {
        this.genresList = genresList;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getFilmStars() {
        return filmStars;
    }

    public void setFilmStars(String filmStars) {
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
