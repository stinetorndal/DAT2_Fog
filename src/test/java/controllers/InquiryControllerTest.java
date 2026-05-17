package controllers;

import app.controllers.InquiryController;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InquiryControllerTest {

    private ConnectionPool connectionPool;

    @BeforeEach
    void setUp() {
        String user = System.getenv("DB_USER");
        String pw = System.getenv("DB_PASS");
        String url = System.getenv("DB_URL");
        connectionPool = ConnectionPool.getInstance("postgres", "postgres", "jdbc:postgresql://localhost:5432/%s", "fog_test");

    }

    @Test
    void testInquiryEndpoint() throws Exception {
        // 1. Setup: Start serveren (hjælpe-metode)
        Javalin app = startTestServer();
        int port = app.port();

        try {
            // 2. Arrange: Klargør data i formParam-kode
            String formData = "længde=600&bredde=400&skur_ja_nej=nej" +
                    "&fornavn=Stine&efternavn=Test&adresse=Vej 1" +
                    "&postnummer=2860&email=test@test.dk";

            // 3. Act: Send data (hjælpe-metode)
            int statusCode = sendPostRequest(port, "/submit-inquiry", formData);

            // 4. Assert: 200 = alt er godt, HTML-side ville vises hvis vi havde en
            assertEquals(200, statusCode);

        } finally {
            app.stop();
        }
    }

    // --- HJÆLPE-METODER ---

    private Javalin startTestServer() {
        // Mini-Javalin-server udelukkende til testbrug
        //Consumer-tekst er alternativ til lambda
        Javalin app = Javalin.create(new java.util.function.Consumer<io.javalin.config.JavalinConfig>() {
            @Override
            public void accept(io.javalin.config.JavalinConfig config) {
                config.fileRenderer(new io.javalin.rendering.FileRenderer() {
                    @Override
                    //Controller kalder ctx.render. Men der er ikke noget at rendere, så dette faker en ok fra render
                    public String render(String path, java.util.Map<String, ?> model, io.javalin.http.Context ctx) {
                        return "Mock HTML for: " + path;
                    }
                });
            }
        });

        new InquiryController().addRoutes(app, connectionPool);
        //Port 0 betyder "find en vilkårlig ledig port og brug den"
        return app.start(0);
    }

    private int sendPostRequest(int port, String path, String data) throws Exception {
        //En falsk browser
        java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create("http://localhost:" + port + path))
                //.header fortæller server at data i pakken er pakket som en formular
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(java.net.http.HttpRequest.BodyPublishers.ofString(data))
                .build();
    //Pakke sendes og vi venter på statuskode. 200 betyder siden er indlæst med success
        return client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString()).statusCode();
    }
}