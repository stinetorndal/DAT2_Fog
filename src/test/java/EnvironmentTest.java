import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EnvironmentTest {

    @Test
    //Denne test tjekker om env-filen overhovedet kan læses, så pw ikke skal hardcodes ind nogle steder
    void canEnvironmentVariablesBeRead() {
        // Arrange: Henter DB_User-variabel fra .env
        String dbUser = System.getenv("DB_USER");

        // Act: Print til konsol for at checke, den er korrekt
        System.out.println("--- ENV TEST-RESULTAT:");
        System.out.println("Værdi af DB_USER er: " + dbUser);
        System.out.println("----------------");

        // Assert: Bestået hvis variabel bliver null
        assertNotNull(dbUser, "env-fil ikke fundet eller læst.");
    }
}
