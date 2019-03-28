package ru.tsypaev.database.backend.dataretrieval.services;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tsypaev.database.backend.dataretrieval.repository.MoviesRepository;

import java.io.IOException;
import java.util.List;

@Service
public class LuceneService {

    @Autowired
    private final MoviesRepository moviesRepository;

    public LuceneService(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public String findMovie() throws IOException, ParseException {


        RAMDirectory ramDirectory = new RAMDirectory();
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriter writer = new IndexWriter(ramDirectory, analyzer, true);

        Document doc = new Document();

        List<String> names = moviesRepository.getNames();

        for (int i = 0; i < names.size(); i++) {
            doc.add(new Field("name", names.get(i), Field.Store.YES, Field.Index.TOKENIZED));
            writer.addDocument(doc);
        }

        writer.close();

        IndexSearcher searcher = new IndexSearcher(ramDirectory);
        QueryParser parser = new QueryParser("name", analyzer);
        Query query = parser.parse("Kiss");
        Hits hits = searcher.search(query);
        Document doc1 = hits.doc(1);
        return doc1.get("name");
    }
}
