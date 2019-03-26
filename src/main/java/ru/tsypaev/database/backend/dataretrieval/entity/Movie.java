package ru.tsypaev.database.backend.dataretrieval.entity;

import javax.persistence.*;

/**
 * @author Tsypaev Vladimir
 */

@Entity
@Table(name = "movies")
public class Movie {
    @Column(name = "year")
    private int year;

    @Column(name = "name")
    private String name;

    @Id
    @Column(name = "id")
    private int id;

    public int getYear() {
        return year;
}

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "uid=" + 1 +
                ", year=" + year +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
