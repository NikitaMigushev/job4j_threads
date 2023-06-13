package ru.job4j.email;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class EmailNotificationTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private EmailNotification emailNotification;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
        emailNotification = new EmailNotification();
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void testEmailTo() {
        User user = new User("John", "john@example.com");
        emailNotification.emailTo(user);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String expectedOutput = "Sending email to: john@example.com\r\n"
                + "Subject: Notification John to email john@example.com\r\n"
                + "Body: Add a new event to John\r\n";
        assertThat(outputStream.toString()).isEqualTo(expectedOutput);
    }
}