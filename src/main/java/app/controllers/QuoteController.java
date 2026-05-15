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

        app.post("/quote/update", ctx -> {ctx.result("New quote version created: " + quoteService.updateQuote(ctx.formParam("quoteId"),
                ctx.formParam("price")).getVersion());
        });

    }


}
