package ru.job4j.thread;

import java.io.*;
import java.net.URL;
import java.util.Properties;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    Properties config;
    private final String filePath;

    public Wget() {
        this.config = new Properties();
        init();
        this.url = config.getProperty("url");
        this.speed = Integer.parseInt(config.getProperty("speed"));
        this.filePath = config.getProperty("folder") + getFileNameFromUrl(url);
    }

    private void init() {
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("thread.properties")) {
            config.load(in);
            validateArg();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void validateArg() {
        if (config.getProperty("url") == null) {
            throw new IllegalArgumentException("Cannot find url in thread.properties file");
        }
        if (config.getProperty("speed") == null) {
            throw new IllegalArgumentException("Cannot find speed in thread.properties file");
        }
        if (config.getProperty("folder") == null) {
            throw new IllegalArgumentException("Cannot find download folder in thread.properties");
        }
    }

    private String getFileNameFromUrl(String url) {
        int lastSlashIndex = url.lastIndexOf('/');
        if (lastSlashIndex == -1 || lastSlashIndex >= url.length()) {
            throw new IllegalArgumentException("Invalid URL format");
        } else {
            return url.substring(lastSlashIndex + 1);
        }
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {

            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long downloadData = 0;
            long startTime = System.currentTimeMillis();

            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                downloadData += bytesRead;
                if (downloadData >= speed) {
                    long currentTIme = System.currentTimeMillis();
                    long pauseTime = currentTIme - startTime;
                    System.out.println("Pause here");
                    Thread.sleep(pauseTime);
                    startTime = System.currentTimeMillis();
                    downloadData = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread wget = new Thread(new Wget());
        wget.start();
        wget.join();
    }
}
