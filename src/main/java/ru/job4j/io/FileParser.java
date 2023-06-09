package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class FileParser {
    private final File file;

    public FileParser(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> filter) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int data;
            while ((data = reader.read()) != -1) {
                char ch = (char) data;
                if (filter.test(ch)) {
                    sb.append(ch);
                }
            }
        }
        return sb.toString();
    }

    public String getContentWithoutUnicode() throws IOException {
        Predicate<Character> unicodeFilter = ch -> ch < 0x80;
        return getContent(unicodeFilter);
    }
}
