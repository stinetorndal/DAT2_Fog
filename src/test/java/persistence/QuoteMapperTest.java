package persistence;

import app.entities.Quote;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.QuoteMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuoteMapperTest {

    private static ConnectionPool connectionPool;

    @BeforeAll
    static void setUpClass() {
        try {
            // Instans af test-connectionPool (ligesom i inquiry-test)
            connectionPool = ConnectionPool.getInstance(null, null, null, null);

            try (Connection connection = connectionPool.getConnection()) {
                try (Statement stmt = connection.createStatement()) {

                    // 1. Slet eksisterende data i rigtig rækkefølge på grund af fremmednøgler
                    stmt.execute("DELETE FROM public.quotes");
                    stmt.execute("DELETE FROM public.inquiries");
                    stmt.execute("DELETE FROM public.salespersons");
                    stmt.execute("DELETE FROM public.customers");

                    // 2. Indsæt 1 test-kunde
                    stmt.execute("INSERT INTO public.customers (first_name, last_name, address, zipcode, email) " +
                            "VALUES ('Test', 'Testesen', 'Testvej 1', 4700, 'test@test.dk')");

                    // 3. Indsæt 1 test-forespørgsel
                    stmt.execute("INSERT INTO public.inquiries (customer_id, carport_length, carport_width, shed_length, shed_width) " +
                            "VALUES ((SELECT max(customer_id) FROM public.customers), 600, 400, 200, 400)");

                    // 4. Indsæt 1 test-sælger
                    stmt.execute("INSERT INTO public.salespersons (salesperson_id, first_name, last_name, email, password, role) " +
                            "VALUES (1, 'Martin', 'Sælger', 'saelger@fog.dk', '1234', 'salesperson')");

                    // 5. Indsæt 1 test-tilbud
                    //SELECT max betyder hvad var det sidte, der blev indsat / nederste række
                    stmt.execute("INSERT INTO public.quotes (inquiry_id, salesperson_id, length, width, price, status, quotation_number, version) " +
                            "VALUES ((SELECT max(inquiry_id) FROM public.inquiries), 1, 600, 400, 12500.00, 'pending', 9001, 1)");

                }
            }
        } catch (SQLException e) {
            System.out.println("Fejl under opsætning af testdata i DB: " + e.getMessage());
        }
    }

    @Test
    void getAllQuotesWithJoin() throws DatabaseException {
        // Arrange
        QuoteMapper quoteMapper = new QuoteMapper();

        // Act: Kald jeres mapper for at hente listen, som udfører SQL-JOIN
        List<Quote> quotes = quoteMapper.getAllQuotes(connectionPool);

        // Assert 1: Tjek om listen overhovedet indeholder noget
        assertNotNull(quotes);
        assertTrue(quotes.size() > 0);

        // Hent tilbud der lige er indsat i databasen
        Quote testQuote = quotes.get(0);

        // Assert 2: Test om JOIN henter kundens rigtige navn ud fra customers-tabellen
        // Databasen burde joine fornavn og efternavn
        assertEquals("Test Testesen", testQuote.getCustomerName());

        // Assert 3: Test om JOIN har hentet målene korrekt op fra inquiries-tabellen
        assertEquals(600, testQuote.getLength());
        assertEquals(400, testQuote.getWidth());

        System.out.println("Succes! Integrationstesten bekræfter at JOIN virker");
        System.out.println("Navn fra DB: " + testQuote.getCustomerName() + " Mål fra DB: " + testQuote.getLength() + "x" + testQuote.getWidth());
    }
}