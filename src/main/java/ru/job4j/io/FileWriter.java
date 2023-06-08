package ru.job4j.io;

import java.io.*;

public class FileWriter {
    private final File file;

    public FileWriter(File file) {
        this.file = file;
    }

    public void saveContent(String content) throws IOException {
        try (OutputStream o = new FileOutputStream(file);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(o))) {
            writer.write(content);
        }
    }
}
