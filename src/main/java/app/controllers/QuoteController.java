package app.controllers;

import app.entities.Quote;
import app.persistence.ConnectionPool;
import app.services.QuoteService;
import io.javalin.Javalin;

public class QuoteController {

    private QuoteService quoteService;

    public QuoteController(){
        this.quoteService = new QuoteService();
    }

    public void addRoutes(Javalin app, ConnectionPool connectionPool) {

        app.post("/quote/update", ctx -> {int quoteId = Integer.parseInt(ctx.formParam("quoteId"));

        double newPrice= Double.parseDouble(ctx.formParam("price"));

        Quote updateQuote = quoteService.updateQuote(quoteId, newPrice);

        ctx.result("New quote version created: " + updateQuote.getVersion());
        });

    }
}
