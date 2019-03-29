package ru.tsypaev.database.backend.dataretrieval.services;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import java.nio.file.Path;
import java.nio.file.Paths;

public class LuceneBinding {
    public static final Path INDEX_PATH = Paths.get(
            System.getProperty("user.home"), "lucene-tutorial-index");
    public static final String YEAR_FIELD = "year";
    public static final String NAME_FIELD = "name";
    public static final String ID_FIELD = "id";

    public static Analyzer getAnalyzer() {
        return new StandardAnalyzer();
    }
}
