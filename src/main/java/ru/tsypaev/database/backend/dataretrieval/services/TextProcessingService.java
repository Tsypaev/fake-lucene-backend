package ru.tsypaev.database.backend.dataretrieval.services;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TextProcessingService {

    public int getYearFromText(String text){
        Pattern pattern = Pattern.compile("\\d{4}");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()){
            return Integer.parseInt(matcher.group());
        }
        return Integer.MIN_VALUE;
    }
}
