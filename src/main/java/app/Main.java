package app;

import app.controllers.InquiryController;
import app.config.SessionConfig;
import app.config.ThymeleafConfig;
import app.controllers.QuoteController;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.services.CarportSvg;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

import java.util.Locale;

public class Main {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(null, null, null, null);

    public static void main(String[] args) {
        // Initializing Javalin and Jetty webserver

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.jetty.modifyServletContextHandler(handler -> handler.setSessionHandler(SessionConfig.sessionConfig()));
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        // Routing
        app.get("/", ctx -> ctx.render("carport.html"));

        InquiryController inquiryController = new InquiryController();
        inquiryController.addRoutes(app, connectionPool);

        QuoteController quoteController = new QuoteController();
        quoteController.addRoutes(app, connectionPool);


//TODO MIDLERTIDIG TESTRUTE - SLET EFTER BRUG
        app.get("/quote", ctx -> {
            try {
                Locale.setDefault(new Locale("US"));
                CarportSvg carportSvg = new CarportSvg(540, 600, connectionPool);
                ctx.attribute("svg", carportSvg.toString());
                ctx.render("quote.html");
            } catch (DatabaseException e) {
                ctx.result(e.getMessage());
            }}
        );

    }
}