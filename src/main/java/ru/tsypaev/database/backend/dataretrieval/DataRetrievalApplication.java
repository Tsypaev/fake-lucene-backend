package ru.tsypaev.database.backend.dataretrieval;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;
import ru.tsypaev.database.backend.dataretrieval.entity.Movie;
import ru.tsypaev.database.backend.dataretrieval.services.MoviesService;

import java.util.List;

@SpringBootApplication
public class DataRetrievalApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataRetrievalApplication.class, args);
	}
}

