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

        try (java.sql.Connection connection = connectionPool.getConnection();
             java.sql.Statement stmt = connection.createStatement()) {

            stmt.execute("DELETE FROM inquiries");
            stmt.execute("DELETE FROM customers");

            stmt.execute("INSERT INTO customers (customer_id, first_name, last_name, address, zipcode, email) VALUES (1, 'Test', 'Testesen', 'Testgade 1', 2860, 'test@test.dk')");

        } catch (java.sql.SQLException e) {
            System.out.println("Fejl: " + e.getMessage());
        }
        testInquiry = new Inquiry(1, 600, 400,  200, 400);

    }
//Test fejler fordi CustomerMapper / saveCustomer skal ændres til int og returnere kunde-id
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


