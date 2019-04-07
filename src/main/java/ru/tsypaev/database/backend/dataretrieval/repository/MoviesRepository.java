package ru.tsypaev.database.backend.dataretrieval.repository;

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

    @Query(value =  "SELECT * FROM data_retrieval.public.movies WHERE name ILIKE (SELECT REPLACE(?1,' ', '%')) LIMIT 10", nativeQuery = true)
    List<Movie> findByTitle(String text);

    @Query(value =  "SELECT * FROM data_retrieval.public.movies WHERE name ILIKE (SELECT REPLACE(?1,' ', '%')) and year = ?2 LIMIT 10", nativeQuery = true)
    List<Movie> findByTitleAndYear(String text, int year);

    @Query(value = "SELECT name FROM data_retrieval.public.movies", nativeQuery = true)
    List<String> getNames();

    @Query(value = "SELECT year FROM data_retrieval.public.movies", nativeQuery = true)
    List<Integer> getYears();

    @Query(value = "SELECT id FROM data_retrieval.public.movies", nativeQuery = true)
    List<Integer> getId();

}
