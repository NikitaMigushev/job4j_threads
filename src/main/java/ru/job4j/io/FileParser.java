package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class FileParser {
    private final File file;

    public FileParser(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> filter) throws IOException {
        try (InputStream i = new FileInputStream(file);
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = i.read(buffer)) != -1) {
                for (int j = 0; j < bytesRead; j++) {
                    char ch = (char) buffer[j];
                    if (filter.test(ch)) {
                        output.write(buffer[j]);
                    }
                }
            }
            return output.toString();
        }
    }

    public String getContentWithoutUnicode() throws IOException {
        Predicate<Character> unicodeFilter = ch -> ch < 0x80;
        return getContent(unicodeFilter);
    }
}
