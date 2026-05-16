package persistence.mappers;

import app.exceptions.DatabaseException;
import app.entities.Inquiry;
import app.persistence.ConnectionPool;
import app.persistence.InquiryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InquiryMapperTest {
    private Inquiry testInquiry;
    private ConnectionPool connectionPool;


    //Arrange / ACT / Assert

    @BeforeEach
    void setUp() {
        String user = System.getenv("DB_USER");
        String pw = System.getenv("DB_PASS");
        String url = System.getenv("DB_URL");
        connectionPool = ConnectionPool.getInstance("postgres", "postgres", "jdbc:postgresql://localhost:5432/%s", "fog_test");
        //connectionPool = ConnectionPool.getInstance(user, pw, url, "fog_test");
        testInquiry = new Inquiry(1, 600, 400,  200, 400);

    }

    @Test
    void saveInquiry()  throws DatabaseException {
        InquiryMapper inquiryMapper = new InquiryMapper();
        //Arrange
        // Act: Vi kalder din mapper og gemmer det ID, databasen sender retur
        int generatedId = inquiryMapper.saveInquiry(testInquiry, connectionPool);

        // Assert: Vi tjekker, om vi fik et ID (hvis det er over 0, er det gemt!)
        assertTrue(generatedId > 0);
        System.out.println("Succes! Forespørgsel gemt i DB med ID: " + generatedId);
    }
}


