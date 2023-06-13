package ru.job4j.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool;

    public EmailNotification() {
        pool = Executors.newFixedThreadPool(10);
    }

    public void emailTo(User user) {
        String subject = "Notification " + user.getUsername() + " to email " + user.getEmail();
        String body = "Add a new event to " + user.getUsername();

        pool.submit(() -> send(subject, body, user.getEmail()));
    }

    private void send(String subject, String body, String email) {
        System.out.println("Sending email to: " + email);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
    }
}
