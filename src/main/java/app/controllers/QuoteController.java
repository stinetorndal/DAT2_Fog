package app.controllers;

import app.entities.Quote;
import app.entities.Salesperson;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.services.QuoteService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class QuoteController {

    private QuoteService quoteService = new QuoteService();

    public void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.post("/sales/inquiry/{id}", ctx -> createQuote(ctx, connectionPool));

    }

    private void createQuote(Context ctx, ConnectionPool connectionPool) {
        try {
            int inquiryId = Integer.parseInt(ctx.pathParam("id"));
            Salesperson salesperson = ctx.sessionAttribute("currentUser");

            if (salesperson != null) {
                int salespersonId = salesperson.getSalespersonId();
                double quotePrice = 1234; //her skal der være et kald til beregning.
                Quote quote = new Quote(inquiryId, salespersonId, quotePrice);
                quoteService.createQuote(quote, connectionPool);
                //TODO skal der komme en meddelelse om, at tilbuddet blev oprettet??
                ctx.redirect("/sales/all-inquiries"); //redirect() fordi ellers vil url'en stadig vise url'en til den enkelte forespørgsel.
            } else {
                ctx.redirect("/login");
            }
        } catch (DatabaseException | NumberFormatException e) {
            ctx.attribute("message", e.getMessage());
            ctx.redirect("/sales/all-inquiries");
        }
        //TODO hvad skal der ske, når en forespørgsel er blevet lavet om til tilbud? Skal den stadig ligge under forespørgsler? Skal den gøre "inaktiv"?
    }
}