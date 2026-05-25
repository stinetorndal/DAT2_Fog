package app.util;


import app.entities.Inquiry;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class CalculateTotalPriceTest {
    private CalculateTotalPrice calculateTotalPrice;
    private Inquiry testInquiry;
    private ConnectionPool connectionPool;

    @BeforeEach
    public void setup() {
        // Arrange = Gør klar før hver test kører
        String user = System.getenv("DB_USER");
        String pw = System.getenv("DB_PASS");
        String url = System.getenv("DB_URL");
        connectionPool = ConnectionPool.getInstance(user, pw, url, "fog_test");
        calculateTotalPrice = new CalculateTotalPrice();
        testInquiry = new Inquiry(1, 600, 600, 0, 0);
    }


    @Test
public void findCorrectTotalPrice () throws DatabaseException {
    // Arrange
    //Stolper = 6, pris = 10 , Remme = 4, pris= (540+300)*2, Spær = 22, pris = 300
    int expectedPrice = 8340;

    //Act
    double result = calculateTotalPrice.calculatePrice(testInquiry, connectionPool);
    System.out.println("Forventet: " + expectedPrice);
    System.out.println("Faktisk: " + result);

    //Assert
    //Checker om listen indeholder noget
    assertTrue(result > 0 , "Prisen burde være over 0 kroner");
    //Checker om samlede pris passer
    assertEquals(expectedPrice, result, 0.01, "Pris matcher ikke antal materialer");
}
}


