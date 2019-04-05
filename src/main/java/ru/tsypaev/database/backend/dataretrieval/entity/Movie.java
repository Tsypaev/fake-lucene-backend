package ru.tsypaev.database.backend.dataretrieval.entity;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return year == movie.year &&
                id == movie.id &&
                Objects.equals(name, movie.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, name, id);
    }
}
