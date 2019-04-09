package ru.tsypaev.database.backend.dataretrieval.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tsypaev.database.backend.dataretrieval.entity.Movie;

import java.util.List;

/**
 * @author Tsypaev Vladimir
 */

@Repository
public interface MoviesRepository extends CrudRepository<Movie, Long> {

    @Query(value = "SELECT * FROM data_retrieval.public.movies WHERE name ILIKE (SELECT REPLACE(?1,' ', '%')) LIMIT 10", nativeQuery = true)
    List<Movie> findByTitle(String text);

    @Query(value = "SELECT * FROM data_retrieval.public.movies WHERE name ILIKE (SELECT REPLACE(?1,' ', '%')) and year = ?2 LIMIT 10", nativeQuery = true)
    List<Movie> findByTitleAndYear(String text, int year);

    @Query(value = "SELECT name FROM data_retrieval.public.movies", nativeQuery = true)
    List<String> getNames();

    @Query(value = "SELECT year FROM data_retrieval.public.movies", nativeQuery = true)
    List<Integer> getYears();

    @Query(value = "SELECT id FROM data_retrieval.public.movies LIMIT 1", nativeQuery = true)
    List<Integer> getId();

    @Modifying
    @Query(value = "update data_retrieval.public.movies set premiere_date = ?1, genre_list = ?2, director = ?3, movie_stars = ?4, annotation = ?5, summary = ?6 where id = ?7", nativeQuery = true)
    void setMoviesInfo(String primiereDate,
                       String genreList,
                       String director,
                       String movieStars,
                       String annotationInteger,
                       String summary,
                       int id
    );

}
