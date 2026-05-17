package app.persistence;

import app.entities.Quote;
import app.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class QuoteMapperTest {

    private final static String USER = "";
    private final static String PASSWORD = "";
    private final static String URL = "";
    private static final String DB = "";

    private static ConnectionPool connectionPool;
    private static QuoteMapper quoteMapper = new QuoteMapper();

    @BeforeAll
    public static void setUpClass() {
        try {
            connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

            try (Connection testConnection = connectionPool.getConnection()) {
                try (Statement stmt = testConnection.createStatement()) {
                    stmt.execute("DROP TABLE IF EXISTS test.zipcodes CASCADE");
                    stmt.execute("DROP TABLE IF EXISTS test.customers CASCADE");
                    stmt.execute("DROP TABLE IF EXISTS test.salespersons CASCADE");
                    stmt.execute("DROP TABLE IF EXISTS test.inquiries CASCADE");
                    stmt.execute("DROP TABLE IF EXISTS test.quotes CASCADE");

                    stmt.execute("DROP SEQUENCE IF EXISTS test.customers_customer_id_seq CASCADE;");
                    stmt.execute("DROP SEQUENCE IF EXISTS test.salespersons_salesperson_id_seq CASCADE;");
                    stmt.execute("DROP SEQUENCE IF EXISTS test.inquiries_inquiry_id_seq CASCADE;");
                    stmt.execute("DROP SEQUENCE IF EXISTS test.quotes_quotation_id_seq CASCADE;");

                    stmt.execute("CREATE TABLE test.zipcodes AS (SELECT * FROM public.zipcodes) WITH NO DATA");
                    stmt.execute("CREATE TABLE test.customers AS (SELECT * FROM public.customers) WITH NO DATA");
                    stmt.execute("CREATE TABLE test.salespersons AS (SELECT * FROM public.salespersons) WITH NO DATA");
                    stmt.execute("CREATE TABLE test.inquiries AS (SELECT * FROM public.inquiries) WITH NO DATA");
                    stmt.execute("CREATE TABLE test.quotes AS (SELECT * FROM public.quotes) WITH NO DATA");

                    stmt.execute("CREATE SEQUENCE test.customers_customer_id_seq");
                    stmt.execute("ALTER TABLE test.customers ALTER COLUMN customer_id SET DEFAULT nextval('test.customers_customer_id_seq')");
                    stmt.execute("CREATE SEQUENCE test.salespersons_salesperson_id_seq");
                    stmt.execute("ALTER TABLE test.salespersons ALTER COLUMN salesperson_id SET DEFAULT nextval('test.salespersons_salesperson_id_seq')");
                    stmt.execute("CREATE SEQUENCE test.inquiries_inquiry_id_seq");
                    stmt.execute("ALTER TABLE test.inquiries ALTER COLUMN inquiry_id SET DEFAULT nextval('test.inquiries_inquiry_id_seq')");
                    stmt.execute("CREATE SEQUENCE test.quotes_quotation_id_seq");
                    stmt.execute("ALTER TABLE test.quotes ALTER COLUMN quotation_id SET DEFAULT nextval('test.quotes_quotation_id_seq')");

                    stmt.execute("ALTER TABLE test.zipcodes ADD PRIMARY KEY (zipcode)");
                    stmt.execute("ALTER TABLE test.customers ADD PRIMARY KEY (customer_id)");
                    stmt.execute("ALTER TABLE test.salespersons ADD PRIMARY KEY (salesperson_id)");
                    stmt.execute("ALTER TABLE test.inquiries ADD PRIMARY KEY (inquiry_id)");
                    stmt.execute("ALTER TABLE test.quotes ADD PRIMARY KEY (quotation_id)");

                    stmt.execute("ALTER TABLE test.customers ADD FOREIGN KEY (zipcode) REFERENCES test.zipcodes(zipcode)");
                    stmt.execute("ALTER TABLE test.inquiries ADD FOREIGN KEY (customer_id) REFERENCES test.customers(customer_id)");
                    stmt.execute("ALTER TABLE test.quotes ADD FOREIGN KEY (inquiry_id) REFERENCES test.inquiries(inquiry_id)");
                    stmt.execute("ALTER TABLE test.quotes ADD FOREIGN KEY (salesperson_id) REFERENCES test.salespersons(salesperson_id)");

                    stmt.execute("ALTER TABLE test.quotes ALTER COLUMN status SET DEFAULT 'pending'");
                    stmt.execute("ALTER TABLE test.quotes ALTER COLUMN version SET DEFAULT 1");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            fail(e.getMessage());
        }
    }

    @BeforeEach
    void setUp() {
        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {

                // Slet data i omvendt rækkefølge af afhængigheder
                stmt.execute("DELETE FROM test.quotes CASCADE");
                stmt.execute("DELETE FROM test.inquiries CASCADE");
                stmt.execute("DELETE FROM test.customers CASCADE");
                stmt.execute("DELETE FROM test.salespersons CASCADE");
                stmt.execute("DELETE FROM test.zipcodes CASCADE");

                // Nulstil sekvenser
                stmt.execute("SELECT setval('test.quotes_quotation_id_seq', 1, false)");
                stmt.execute("SELECT setval('test.inquiries_inquiry_id_seq', 1, false)");
                stmt.execute("SELECT setval('test.customers_customer_id_seq', 1, false)");
                stmt.execute("SELECT setval('test.salespersons_salesperson_id_seq', 1, false)");

                // Indsæt postnumre
                stmt.execute("INSERT INTO test.zipcodes (zipcode, city) VALUES " +
                        "(2800, 'Kongens Lyngby'), " +
                        "(8000, 'Aarhus')");

                // Indsæt kunder
                stmt.execute("INSERT INTO test.customers (firstname, last_name, address, zipcode, email) VALUES " +
                        "('Anders', 'Hansen', 'Nørregade 1', 2800, 'anders@mail.dk'), " +
                        "('Sofie', 'Jensen', 'Vestergade 2', 8000, 'sofie@mail.dk')");

                // Indsæt sælgere
                stmt.execute("INSERT INTO test.salespersons (firstname, last_name, email, password, role) VALUES " +
                        "('John', 'Doe', 'john@fog.dk', 'password123', 'salesperson'), " +
                        "('Jane', 'Smith', 'jane@fog.dk', 'password123', 'salesperson')");

                // Indsæt forespørgsler
                stmt.execute("INSERT INTO test.inquiries (customer_id, carport_length, carport_width, shed_length, shed_width) VALUES " +
                        "(1, 600, 400, 200, 200), " +
                        "(2, 500, 300, 0, 0)");

                // Indsæt tilbud
                stmt.execute("INSERT INTO test.quotes (inquiry_id, salesperson_id, price) VALUES " +
                        "(1, 1, 12495), " +
                        "(2, 2, 9995)");
            }
        } catch (SQLException throwables) {
            fail(throwables.getMessage());
        }
    }

    @Test
    void createQuote() throws DatabaseException {
        // Arrange
        Quote quote = new Quote(3, 1, 49995);

        // Act
        int quotationId = quoteMapper.createQuote(quote, connectionPool);

        // Assert
        assertEquals(3, quotationId);
    }
}