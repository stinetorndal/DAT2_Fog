package app.controllers;

import app.entities.Inquiry;
import app.entities.Quote;
import app.entities.Salesperson;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.services.InquiryService;
import app.services.QuoteService;
import app.services.CalculateTotalPrice;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;

public class QuoteController {

    private InquiryService inquiryService = new InquiryService();
    private QuoteService quoteService = new QuoteService();
    private CalculateTotalPrice calculateTotalPrice = new CalculateTotalPrice();

    public void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.post("/sales/inquiry/{id}", ctx -> createQuote(ctx, connectionPool));

        app.get("/sales-dashboard/all-quotations", ctx -> viewAllQuotations(ctx, connectionPool));
    }

    private void createQuote(Context ctx, ConnectionPool connectionPool) {
    private void viewAllQuotations (Context ctx, ConnectionPool connectionPool) {
        try {
            int inquiryId = Integer.parseInt(ctx.pathParam("id"));
            Inquiry inquiry = inquiryService.getInquiryById(inquiryId, connectionPool);
            Salesperson salesperson = ctx.sessionAttribute("currentUser");
            List<Quote> quotes = quoteService.handleAllQuotes(connectionPool);

            if (salesperson != null) {
                int salespersonId = salesperson.getSalespersonId();
                int length = inquiry.getCarportLength();
                int width = inquiry.getCarportWidth();
                double quotePrice = calculateTotalPrice.calculatePrice(inquiry, connectionPool);
                int quotationNumber = quoteService.getQuotationNumber(connectionPool);
                Quote quote = new Quote(inquiryId, salespersonId, length, width, quotePrice, quotationNumber);
                quoteService.createQuote(quote, connectionPool);
                ctx.redirect("/sales/all-inquiries"); //redirect() fordi ellers vil url'en stadig vise url'en til den enkelte forespørgsel.
            } else {
                ctx.redirect("/login");
            }
        } catch (DatabaseException | NumberFormatException e) {
            ctx.attribute("message", e.getMessage());
            ctx.redirect("/sales/all-inquiries");
            //Så Thymeleaf kan læse den
            ctx.attribute("alleTilbud", quotes);
            ctx.render("sales_quotations.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", "Kunne ikke hente alle tilbud " + e.getMessage());
            ctx.render("sales_dashboard.html");
        }
    }
}
}