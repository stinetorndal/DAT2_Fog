package app;

import app.config.SessionConfig;
import app.config.ThymeleafConfig;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

public class Main {

    //Opretter en instans af ConnectionPool. Den er både static og final - det betyder, at der kun oprettes én connectionPool,
    // og at den ikke kan ændres bagefter. (Og det hænger godt sammen med Singleton-mønsteret).
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(null, null, null, null);

    public static void main(String[] args) {

        // Initializing Javalin and Jetty webserver
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.jetty.modifyServletContextHandler(handler -> handler.setSessionHandler(SessionConfig.sessionConfig()));
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        // Routing
        app.get("/", ctx -> ctx.render("index.html"));

    }
}