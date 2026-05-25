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
        app.get("/sales-dashboard/all-quotations", ctx -> viewAllQuotations(ctx, ConnectionPool));
    }

    private void viewAllQuotations
}
