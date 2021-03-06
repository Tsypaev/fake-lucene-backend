package ru.tsypaev.database.backend.dataretrieval.services.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class with properties for lucene
 * @author Vladimir Tsypaev
 */
class LuceneBinding {
    static final Path INDEX_PATH = Paths.get(
            System.getProperty("user.home"), "lucene-tutorial-index");
    static final String YEAR_FIELD = "year";
    static final String NAME_FIELD = "name";
    static final String ID_FIELD = "id";

    static Analyzer getAnalyzer() {
        return new StandardAnalyzer();
    }
}
