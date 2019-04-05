package ru.tsypaev.database.backend.dataretrieval.services;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tsypaev.database.backend.dataretrieval.entity.Movie;
import ru.tsypaev.database.backend.dataretrieval.repository.MoviesRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.lucene.document.Field.Store.YES;

@Service
public class LuceneService {

    public static final int LIMIT = 10;

    @Autowired
    private final MoviesRepository moviesRepository;
    private TextProcessingService textProcessingService;

    public LuceneService(MoviesRepository moviesRepository, TextProcessingService textProcessingService) {
        this.moviesRepository = moviesRepository;
        this.textProcessingService = textProcessingService;
    }

    public List<Movie> searchLucene(String text, String type) throws Exception {

        List<Movie> movies = new ArrayList<>();
        Directory directory = writeIndex();
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

        if (type.equals("all")){
            List<TopDocs> topDocs = searchByFewWords(text, searcher);

            for (int i = 0; i < topDocs.size(); i++) {
                if (i < 1) {
                    addMoviesToList(movies, searcher, topDocs.get(i));
                } else {
                    List<Movie> movies1 = addFakeMoviesToList(searcher, topDocs.get(i));
                    movies.retainAll(movies1);
                }
            }

            reader.close();

            return movies;
        } else {
            int yearFromText = textProcessingService.getYearFromText(text);
            String processingString = textProcessingService.deleteYearFromText(text, yearFromText);
            List<TopDocs> topDocs = searchByTitleAndYear(processingString, String.valueOf(yearFromText), searcher);
            for (int i = 0; i < topDocs.size(); i++) {
                addMoviesToList(movies, searcher, topDocs.get(i));
            }
            reader.close();

            return movies;
        }
    }

    private Directory writeIndex() throws IOException {
        Directory directory = FSDirectory.open(LuceneBinding.INDEX_PATH);
        IndexWriterConfig writerConfig = new IndexWriterConfig(LuceneBinding.getAnalyzer());
        writerConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter indexWriter = new IndexWriter(directory, writerConfig);

        List<String> names = moviesRepository.getNames();
        List<Integer> years = moviesRepository.getYears();
        List<Integer> uids = moviesRepository.getId();

        for (int i = 0; i < names.size(); i++) {
            final Document doc = new Document();
            doc.add(new StringField(LuceneBinding.ID_FIELD, uids.get(i).toString(), YES));
            doc.add(new TextField(LuceneBinding.NAME_FIELD, names.get(i), YES));
            doc.add(new StringField(LuceneBinding.YEAR_FIELD, years.get(i).toString(), YES));
            System.out.println(i);
            indexWriter.addDocument(doc);
        }

        indexWriter.commit();
        indexWriter.close();

        return directory;
    }

    private void addMoviesToList(List<Movie> movies, IndexSearcher searcher, TopDocs foundDocs) throws IOException {

        for (ScoreDoc docs : foundDocs.scoreDocs) {
            Movie movie = createMovieFromDocs(searcher, docs);

            if (movies.contains(movie)) {
                return;
            }

            movies.add(movie);
        }

    }

    private List<Movie> addFakeMoviesToList(IndexSearcher searcher, TopDocs foundDocs) throws IOException {

        List<Movie> newList = new ArrayList<>();

        for (ScoreDoc docs : foundDocs.scoreDocs) {
            Movie movie = createMovieFromDocs(searcher, docs);

            newList.add(movie);
        }
        return newList;

    }

    private Movie createMovieFromDocs(IndexSearcher searcher, ScoreDoc docs) throws IOException {
        Document d = searcher.doc(docs.doc);

        Movie movie = new Movie();

        movie.setName(d.get(LuceneBinding.NAME_FIELD));
        movie.setYear(Integer.parseInt(d.get(LuceneBinding.YEAR_FIELD)));
        movie.setId(Integer.parseInt(d.get(LuceneBinding.ID_FIELD)));
        return movie;
    }

    private static TopDocs searchByName(String name, IndexSearcher searcher) throws Exception {
        QueryParser qp = new QueryParser("name", new StandardAnalyzer());
        Query firstNameQuery = qp.parse(name);
        TopDocs hits = searcher.search(firstNameQuery, LIMIT);
        return hits;
    }

    private static TopDocs searchByPathName(String name, IndexSearcher searcher) throws Exception {
        WildcardQuery wildcardQuery = new WildcardQuery(new Term("name", "*" + name + "*"));
        TopDocs hits = searcher.search(wildcardQuery, LIMIT);
        return hits;
    }

    private static List<TopDocs> searchByFewWords(String name, IndexSearcher searcher) throws Exception {
        List<TopDocs> topDocs = new ArrayList<>();
        String[] words = name.split(" ");

        if (words.length == 1) {
            if (searchByName(name, searcher).totalHits.value == 0) {
                topDocs.add(searchByPathName(name, searcher));
                return topDocs;
            } else {
                topDocs.add(searchByName(name, searcher));
                return topDocs;
            }
        }

        for (int i = 0; i < words.length; i++) {
            topDocs.add(searchByPathName(words[i], searcher));
        }
        return topDocs;
    }

    private static TopDocs searchByYear(String name, IndexSearcher searcher) throws Exception {
        QueryParser qp = new QueryParser("year", new StandardAnalyzer());
        Query firstNameQuery = qp.parse(name);
        TopDocs hits = searcher.search(firstNameQuery, LIMIT);
        return hits;
    }

    private static List<TopDocs> searchByTitleAndYear(String name, String year, IndexSearcher searcher) throws Exception {
        List<TopDocs> topDocs = new ArrayList<>();

        TopDocs docName = searchByPathName(name, searcher);
        TopDocs docYear = searchByYear(year, searcher);

        topDocs.add(docName);
        topDocs.add(docYear);

        return topDocs;
    }
}
