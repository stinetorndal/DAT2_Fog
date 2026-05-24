package app.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class EmailServiceTest {

    @Test
    void testEmailService () {
        //Arrange
        EmailService emailService = new EmailService();
        String toEmail = "testkunde@example.com";
        String subject = "JUnit Integration Test";
        String bodyText = "Dette er en test";

        //Act and Assert
        try {
            emailService.sendEmail(toEmail, subject, bodyText);

            assertTrue(true);
        } catch (Exception e) {
            fail("Fejl da email skulle sendes" + e.getMessage());

        }
    }
}
