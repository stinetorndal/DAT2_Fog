package app.services;

import app.entities.Inquiry;
import app.entities.Material;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    private Calculator calculator;
    private Inquiry testInquiry;
    private Inquiry testInquiry2;
    private ConnectionPool connectionPool;

    @BeforeEach
    public void setup() {
        // Arrange = Gør klar før hver test kører
        String user = System.getenv("DB_USER");
        String pw = System.getenv("DB_PASS");
        String url = System.getenv("DB_URL");
        connectionPool = ConnectionPool.getInstance(user, pw, url, "fog_test");
        int length = 600;
        int width = 600;
        calculator = new Calculator(length, width);

        // Testforespørgsel på gyldige mål
        testInquiry = new Inquiry(1, length, width, 0, 0);

    }

    @Test  //Hvis der ikke står test, er det bare en hjælpemetode
    public void isLongestBeam540cm() {

    }

    @Test
    //Positiv test - er antal spær rigtigt regnet ud ift valgt bredde
    public void findCorrectNumberOfRafts() throws DatabaseException {
        // Arrange - testInquiry.
        // Forventet antal spær: 600/55 = 10,9, rundes op til 11, ganges med 2
        int expectedAmount = 22;

        // Act:
        List<Material> result = calculator.calculateRafts(testInquiry.getCarportLength(), testInquiry.getCarportWidth(), connectionPool);
        System.out.println("Forventet antal: " + expectedAmount);
        System.out.println("Faktisk antal: " + result.size());

        // Assert:
        assertEquals(expectedAmount, result.size(), "Antallet af spær er forkert ift bredde");
    }


    @Test
    public void findCorrectNumberOfBeams() throws DatabaseException{
        // Arrange - testInquiry. 600 1x540+1x300
        // Længste rem = 540 cm + den der passer bedst med restlængde
        int expectedAmount = 4;

        // Act:
        List<Material> result = calculator.calculateBeams(testInquiry.getCarportLength(), connectionPool);
        System.out.println("Forventet antal: " + expectedAmount);
        System.out.println("Faktisk antal: " + result.size());

        // Assert:
        assertEquals(expectedAmount, result.size(), "Forkerte længde remme ift længde");
    }

    @Test
    public void findCorrectNumberOfPosts () throws DatabaseException{
        // Arrange
        //postsOneSide = (inquiry.getCarportLength() - frontudhæng - bagudhæng) / ((max_afstand - min_afstand) + stolpebredde);
        //int totalAmountPosts = (int) Math.ceil(postsOneSide) * 2;
        // (600-100-20) / ((300-150) + 10) = 3,0 på hver side x 2
        int expectedAmount = 6;

        //Act
        List<Material> result = calculator.calculatePosts(testInquiry.getCarportLength(), connectionPool);
        System.out.println("Forventet antal: " + expectedAmount);
        System.out.println("Faktisk antal: " + result.size());

        // Assert
        assertEquals(expectedAmount, result.size(), "Forkert antal stolper");
    }
}

