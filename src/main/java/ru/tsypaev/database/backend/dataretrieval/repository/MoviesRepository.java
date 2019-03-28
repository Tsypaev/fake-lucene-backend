package ru.tsypaev.database.backend.dataretrieval.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tsypaev.database.backend.dataretrieval.entity.Movie;

import java.util.List;

@Repository
public interface MoviesRepository extends CrudRepository<Movie, Long> {

    @Query(value =  "SELECT * FROM data_retrieval.public.movies WHERE name like ?1 UNION ALL SELECT * FROM data_retrieval.public.movies WHERE year = ?2 LIMIT 10", nativeQuery = true)
    List<Movie> universalFinder(String text, int year);

    @Query(value = "SELECT name FROM data_retrieval.public.movies LIMIT 10", nativeQuery = true)
    List<String> getNames();

}
