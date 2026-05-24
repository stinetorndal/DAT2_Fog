package app.services;

import app.entities.Inquiry;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InquiryServiceTest {
    private ConnectionPool connectionPool;
    private InquiryService inquiryService;

    @BeforeEach
    void setUp() {
        String user = System.getenv("DB_USER");
        String pw = System.getenv("DB_PASS");
        String url = System.getenv("DB_URL");

        connectionPool = ConnectionPool.getInstance(user, pw, url, "fog_test");
        inquiryService = new InquiryService();
    }

        @Test
        void doesInquiryServiceCommunicateWithMapper () throws DatabaseException {
            // Arrange: Lav ny forespørgsel
        Inquiry testInquiry = new Inquiry(1, 600, 400, 0,0);

        // Act:
        inquiryService.handleInquiry(testInquiry, connectionPool);
            // Assert: Er automatisk datostempel sat ind
            assertNotNull(testInquiry.getDate(), "Datoen skulle gerne være sat automatisk i objektet.");


            System.out.println("Succes! : " + testInquiry.getDate());
        }

    }

