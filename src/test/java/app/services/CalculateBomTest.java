package app.services;

import app.entities.Inquiry;
import app.entities.Material;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CalculateBomTest {

    private CalculateBom calculateBom;
    private Inquiry testInquiry;
    private ConnectionPool connectionPool;

    @BeforeEach
    public void setup() {
        // Arrange = Gør klar før hver test kører
        String user = System.getenv("DB_USER");
        String pw = System.getenv("DB_PASS");
        String url = System.getenv("DB_URL");
        connectionPool = ConnectionPool.getInstance(user, pw, url, "fog_test");
        calculateBom = new CalculateBom();
        testInquiry = new Inquiry(1, 600, 600, 0, 0);
    }

    @Test
    public void findCorrectTotalAmountInBom () throws DatabaseException{
        // Arrange
        //Stolper = 6, Remme = 4 stk, Spær = 22
        int expectedMaterials = 32;

        //Act
        List<Material> result = calculateBom.calculateCarport(testInquiry, connectionPool);
        System.out.println("Forventet: " + expectedMaterials);
        System.out.println("Faktisk: " + result);

        //Assert
        //Checker om listen indeholder noget
        assertFalse(result.isEmpty(), "Stykliste burde ikke være tom");
        //Checker om samlet antal passer
        assertEquals(expectedMaterials, result.size(), "Samlede antal matcher ikke beregninger");
    }
}

