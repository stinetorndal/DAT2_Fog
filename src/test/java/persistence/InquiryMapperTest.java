package persistence;

import app.entities.Customer;
import app.entities.Zipcode;
import app.exceptions.DatabaseException;
import app.entities.Inquiry;
import app.persistence.ConnectionPool;
import app.persistence.InquiryMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class InquiryMapperTest {
    private static Inquiry testInquiry;
    private static ConnectionPool connectionPool;


    //Arrange / ACT / Assert

    @BeforeAll
    static void setUpClass() {
        try {
            connectionPool = ConnectionPool.getInstance(null, null, null, null);

            try (Connection connection = connectionPool.getConnection()) {
                try (Statement stmt = connection.createStatement()) {

                    stmt.execute("DELETE FROM inquiries");
                    stmt.execute("DELETE FROM customers");
                    stmt.execute("INSERT INTO customers (customer_id, first_name, last_name, address, zipcode, email) " +
                            "VALUES (1, 'test', 'testefternavn', 'testvej 1', 4700, 'test@test.dk')");

                }
            }
        } catch (SQLException e) {
                System.out.println("Fejl da der skulle slettes/indsættes fra DB: " + e.getMessage());
            }
        }


    @Test
    void saveInquiry()  throws DatabaseException {
        InquiryMapper inquiryMapper = new InquiryMapper();
            testInquiry = new Inquiry(1, 600, 400, 200, 400);
        //Arrange
        // Act: Kalder mapper og gemmer det ID, databasen sender retur
        int generatedId = inquiryMapper.saveInquiry(testInquiry, connectionPool);

        // Assert: Check om ID returneres (hvis det er over 0, er det gemt)
        assertTrue(generatedId > 0);
        System.out.println("Succes! Forespørgsel gemt i DB med ID: " + generatedId);
    }
}


