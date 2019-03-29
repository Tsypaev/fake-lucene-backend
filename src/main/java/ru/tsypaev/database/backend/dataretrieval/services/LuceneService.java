package ru.tsypaev.database.backend.dataretrieval.services;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tsypaev.database.backend.dataretrieval.repository.MoviesRepository;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Service
public class LuceneService {

    private IndexWriter indexWriter = null;

    @Autowired
    private final MoviesRepository moviesRepository;

    public LuceneService(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public String searchLucene(String query) throws IOException, ParseException {

        final Directory directory = FSDirectory.open(LuceneBinding.INDEX_PATH);
        final IndexWriterConfig iwConfig = new IndexWriterConfig(LuceneBinding.getAnalyzer());
        iwConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        this.indexWriter = new IndexWriter(directory, iwConfig);

        final Document doc = new Document();

        final FieldType nameType = new FieldType();
        nameType.setIndexOptions(IndexOptions.DOCS);
        nameType.setStored(true);
        nameType.setTokenized(false);
        nameType.setStoreTermVectorOffsets(false);
        nameType.setStoreTermVectorPayloads(false);
        nameType.setStoreTermVectorPositions(false);
        nameType.setStoreTermVectors(false);

        List<String> names = moviesRepository.getNames();


        for (int i = 0; i < names.size(); i++) {
            doc.add(new Field(LuceneBinding.NAME_FIELD, names.get(i), nameType));
            indexWriter.addDocument(doc);
            System.out.println(i);
        }

        indexWriter.close();

        Directory dir = FSDirectory.open(Paths.get(String.valueOf(LuceneBinding.INDEX_PATH)));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);

        QueryParser qp = new QueryParser("name", new StandardAnalyzer());
        Query idQuery = qp.parse(query);
        TopDocs hits = searcher.search(idQuery, 10);

        for (ScoreDoc sd : hits.scoreDocs)
        {
            Document d = searcher.doc(sd.doc);
            System.out.println(String.format(d.get("name")));
        }

        System.out.println("Конец");
        return "test";
    }
}
