package ru.job4j.io;

import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        File file = new File("./data/example.txt");  // Replace with your file path
        FileParser fileParser = new FileParser(file);
        try {
            String contentWithoutUnicode = fileParser.getContentWithoutUnicode();
            System.out.println("Content without unicode:");
            System.out.println(contentWithoutUnicode);
            Predicate<Character> filter = ch -> Character.isUpperCase(ch);
            String filteredContent = fileParser.getContent(filter);
            System.out.println("Filtered content (Uppercase only):");
            System.out.println(filteredContent);
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }
}
