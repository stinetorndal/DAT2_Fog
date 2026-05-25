package app.controllers;

import app.entities.Quote;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.services.QuoteService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;

public class QuoteController {
    private QuoteService quoteService = new QuoteService();

    public void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/sales-dashboard/all-quotations", ctx -> viewAllQuotations(ctx, connectionPool));
    }

    private void viewAllQuotations (Context ctx, ConnectionPool connectionPool) {
        try {
            List<Quote> quotes = quoteService.handleAllQuotes(connectionPool);

            //Så Thymeleaf kan læse den
            ctx.attribute("alleTilbud", quotes);
            ctx.render("sales_quotations.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", "Kunne ikke hente alle tilbud " + e.getMessage());
            ctx.render("sales_dashboard.html");
        }
    }

}
