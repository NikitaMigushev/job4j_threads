package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("downloaded_file")) {

            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long startTime = System.currentTimeMillis();
            long elapsedTime;
            long expectedElapsedTime;

            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);

                elapsedTime = System.currentTimeMillis() - startTime;
                expectedElapsedTime = (long) (bytesRead / (double) speed * 1000);

                if (elapsedTime < expectedElapsedTime) {
                    Thread.sleep(expectedElapsedTime - elapsedTime);
                }

                startTime = System.currentTimeMillis();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 2) {
            System.out.println("Usage: java Wget <url> <speed>");
            return;
        }

        String url = args[0];
        int speed;

        try {
            speed = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid speed value. Speed must be an integer.");
            return;
        }

        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
