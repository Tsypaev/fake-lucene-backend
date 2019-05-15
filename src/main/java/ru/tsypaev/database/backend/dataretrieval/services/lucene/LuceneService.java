package ru.tsypaev.database.backend.dataretrieval.services.lucene;

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
import ru.tsypaev.database.backend.dataretrieval.services.TextUtilService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.lucene.document.Field.Store.YES;

/**
 * Class for searching via Lucene
 * @author Vladimir Tsypaev
 */

@Service
public class LuceneService {

    private static final int LIMIT = 10;

    @Autowired
    private final MoviesRepository moviesRepository;
    private TextUtilService textUtilService;

    public LuceneService(MoviesRepository moviesRepository, TextUtilService textUtilService) {
        this.moviesRepository = moviesRepository;
        this.textUtilService = textUtilService;
    }

    /**
     *
     * @param text - searching text
     * @param type - type of searching
     * @return founf movies
     * @throws Exception
     */
    public List<Movie> searchLucene(String text, String type) throws Exception {

        List<Movie> movies = new ArrayList<>();
        Directory directory = writeIndex();
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

        switch (type) {
            case "all": {
                List<TopDocs> topDocs = searchByFewWords(text, searcher);
                dirtyHack(movies, searcher, topDocs);
                reader.close();

                return movies;
            }
            case "year": {
                int yearFromText = textUtilService.getYearFromText(text);
                String processingString = textUtilService.deleteYearFromText(text, yearFromText);
                if(processingString.equals("")){
                    TopDocs topDocs = searchByYear(String.valueOf(yearFromText), searcher);
                    for (int i = 0; i < topDocs.scoreDocs.length; i++) {
                        Movie movie = createMovieFromDocs(searcher, topDocs.scoreDocs[i]);
                        movies.add(movie);
                    }
                } else {
                    List<TopDocs> topDocs = searchByTitleAndYear(processingString, String.valueOf(yearFromText), searcher);
                    dirtyHack(movies, searcher, topDocs);
                }
                reader.close();

                return movies;
            }
            default:
                return null;
        }
    }

    private void dirtyHack(List<Movie> movies, IndexSearcher searcher, List<TopDocs> topDocs) throws IOException {
        for (int i = 0; i < topDocs.size(); i++) {
            if (i < 1) {
                addMoviesToList(movies, searcher, topDocs.get(i));
            } else {
                List<Movie> movies1 = addFakeMoviesToList(searcher, topDocs.get(i));
                movies.retainAll(movies1);
            }
        }
    }

    /**
     *
     * @return directory with indexes
     * @throws IOException
     */
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

    /**
     *
     * @param searcher - indexSearcher
     * @param docs - found movie
     * @return instance of Movie.class
     * @throws IOException
     */
    private Movie createMovieFromDocs(IndexSearcher searcher, ScoreDoc docs) throws IOException {
        Document d = searcher.doc(docs.doc);

        Movie movie = new Movie();

        movie.setName(d.get(LuceneBinding.NAME_FIELD));
        movie.setYear(Integer.parseInt(d.get(LuceneBinding.YEAR_FIELD)));
        movie.setId(Integer.parseInt(d.get(LuceneBinding.ID_FIELD)));

        return movie;
    }

    /**
     *
     * @param name - name of movie
     * @param searcher - indexSearcher
     * @return found movie
     * @throws Exception
     */
    private static TopDocs searchByName(String name, IndexSearcher searcher) throws Exception {
        QueryParser qp = new QueryParser("name", new StandardAnalyzer());
        Query firstNameQuery = qp.parse(name);
        return searcher.search(firstNameQuery, LIMIT);
    }

    /**
     *
     * @param name - name of movie
     * @param searcher - indexSearcher
     * @return found movie
     * @throws Exception
     */
    private static TopDocs searchByPathName(String name, IndexSearcher searcher) throws Exception {
        WildcardQuery wildcardQuery = new WildcardQuery(new Term("name", "*" + name + "*"));
        return searcher.search(wildcardQuery, LIMIT);
    }

    /**
     *
     * @param name - name of movie
     * @param searcher - indexSearcher
     * @return found movies
     * @throws Exception
     */
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

        for (String word : words) {
            topDocs.add(searchByPathName(word, searcher));
        }
        return topDocs;
    }

    /**
     *
     * @param name - name of movie
     * @param searcher - indexSearcher
     * @return found movie
     * @throws Exception
     */
    private static TopDocs searchByYear(String name, IndexSearcher searcher) throws Exception {
        QueryParser qp = new QueryParser("year", new StandardAnalyzer());
        Query firstNameQuery = qp.parse(name);
        return searcher.search(firstNameQuery, LIMIT);
    }

    /**
     *
     * @param name - name of movie
     * @param year - year of movie
     * @param searcher - indexSearcher
     * @return found movies
     * @throws Exception
     */
    private static List<TopDocs> searchByTitleAndYear(String name, String year, IndexSearcher searcher) throws Exception {
        List<TopDocs> topDocs = new ArrayList<>();

        TopDocs docName = searchByPathName(name, searcher);
        TopDocs docYear = searchByYear(year, searcher);

        topDocs.add(docName);
        topDocs.add(docYear);

        return topDocs;
    }
}
