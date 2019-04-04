package ru.tsypaev.database.backend.dataretrieval.services;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
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

    private IndexWriter indexWriter = null;
    private IndexReader indexReader = null;

    @Autowired
    private final MoviesRepository moviesRepository;

    public LuceneService(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public List<Movie> searchLucene(String query) throws Exception {

        List<Movie> movies = new ArrayList<>();

        final Directory directory = FSDirectory.open(LuceneBinding.INDEX_PATH);
        final IndexWriterConfig iwConfig = new IndexWriterConfig(LuceneBinding.getAnalyzer());
        iwConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        this.indexWriter = new IndexWriter(directory, iwConfig);

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

        this.indexReader = DirectoryReader.open(directory);

        IndexSearcher searcher = new IndexSearcher(indexReader);

        TopDocs foundDocs2 = searchByFirstName(query, searcher);

        System.out.println("Toral Results :: " + foundDocs2.totalHits);

        for (ScoreDoc sd : foundDocs2.scoreDocs)
        {
            Document d = searcher.doc(sd.doc);
            Movie movie = new Movie();
            movie.setName(d.get(LuceneBinding.NAME_FIELD));
            movie.setYear(Integer.parseInt(d.get(LuceneBinding.YEAR_FIELD)));
            movie.setId(Integer.parseInt(d.get(LuceneBinding.ID_FIELD)));
            movies.add(movie);
        }

        indexReader.close();
        return movies;
    }

    private static TopDocs searchByFirstName(String name, IndexSearcher searcher) throws Exception
    {
        QueryParser qp = new QueryParser("name", new StandardAnalyzer());
        Query firstNameQuery = qp.parse(name);
        TopDocs hits = searcher.search(firstNameQuery, 10);
        return hits;
    }

    private static IndexSearcher createSearcher() throws IOException {
        Directory dir = FSDirectory.open(LuceneBinding.INDEX_PATH);
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        return searcher;
    }

}
