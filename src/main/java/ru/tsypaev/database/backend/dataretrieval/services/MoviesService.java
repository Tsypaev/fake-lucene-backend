package ru.tsypaev.database.backend.dataretrieval.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tsypaev.database.backend.dataretrieval.entity.Movie;
import ru.tsypaev.database.backend.dataretrieval.repository.MoviesRepository;

import java.util.List;

@Service
public class MoviesService {

    @Autowired
    private final MoviesRepository moviesRepository;

    public MoviesService(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public List<Movie> findText(String text) {
        return moviesRepository.findByTitle(text);
    }

    public List<Movie> findText(String text, int year) {
        return moviesRepository.findByTitleAndYear(text, year);
    }
}
