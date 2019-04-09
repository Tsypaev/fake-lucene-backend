package ru.tsypaev.database.backend.dataretrieval.services;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TextProcessingService {

    public int getYearFromText(String text) {
        Pattern pattern = Pattern.compile("\\d{4}");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }
        return Integer.MIN_VALUE;
    }

    public static String addZeros(Integer id) {
        StringBuilder s = new StringBuilder(String.valueOf(id));
        if (s.length() != 7) {
            while (s.length() != 7) {
                s.insert(0, "0");
            }
        }
        return s.toString();
    }

    public String deleteYearFromText(String text, int year) {
        return text.replaceAll(String.valueOf(year), "");
    }
}
