package app.controllers;

import app.entities.Customer;
import app.entities.Inquiry;
import app.entities.Quote;
import app.entities.Salesperson;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.services.*;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class QuoteController {

    private InquiryService inquiryService = new InquiryService();
    private QuoteService quoteService = new QuoteService();
    private CalculateTotalPrice calculateTotalPrice;
    private CustomerService customerService = new CustomerService();

    public void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.post("convertToQuote", ctx -> createQuote(ctx, connectionPool));
        app.get("/sales/quote/{id}", ctx -> showQuote(ctx, connectionPool));
        app.get("/sales-quotations", ctx -> viewAllQuotations(ctx, connectionPool));
        app.get("/sales_quotation_details/{id}", ctx -> showQuote(ctx, connectionPool));
    }

    private void createQuote(Context ctx, ConnectionPool connectionPool) {
        try {
            int inquiryId = Integer.parseInt(ctx.formParam("id"));
            Inquiry inquiry = inquiryService.getInquiryById(inquiryId, connectionPool);
            calculateTotalPrice = new CalculateTotalPrice(inquiry.getCarportLength(), inquiry.getCarportWidth());
            Salesperson salesperson = ctx.sessionAttribute("currentUser");

            if (salesperson != null) {
                int salespersonId = salesperson.getSalespersonId();
                int length = inquiry.getCarportLength();
                int width = inquiry.getCarportWidth();
                double quotePrice = calculateTotalPrice.calculatePrice(length, width, connectionPool);
                Quote quote = new Quote(inquiryId, salespersonId, length, width, quotePrice);
                int quotationId = quoteService.createQuote(quote, connectionPool);

                ctx.redirect("sales_quotation_details/" + quotationId); //redirect() fordi ellers vil url'en stadig vise url'en til den enkelte forespørgsel.
            } else {
                ctx.redirect("/login");
            }
        } catch (DatabaseException | NumberFormatException e) {
            ctx.attribute("message", e.getMessage());
            ctx.redirect("/sales_inquiries");
        }
    }

    private void showQuote(Context ctx, ConnectionPool connectionPool) {
        try {
            int quotationId = Integer.parseInt(ctx.pathParam("id"));
            Quote quote = quoteService.getQuoteById(quotationId, connectionPool);

            Customer customerObject = customerService.getCustomerByQuote(quotationId, connectionPool);

            ctx.attribute("customer", customerObject);
            ctx.attribute("quote", quote);
            ctx.render("sales_quotation_details.html");
        } catch (DatabaseException | NumberFormatException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("sales_quotation.html");
        }
    }

    private void showSvg(Context ctx, ConnectionPool connectionPool) {
        try {
            //TODO Flyt eventuelt til anden metode
            //CarportSvg carportSvg = new CarportSvg(quote.getLength(), quote.getWidth(), connectionPool);
            //ctx.attribute("svg", carportSvg.toString());
        }
    }

    private void viewAllQuotations(Context ctx, ConnectionPool connectionPool) {
        try {
            List<Quote> quotes = quoteService.handleAllQuotes(connectionPool);

            //Så Thymeleaf kan læse den
            ctx.attribute("allQuotations", quotes);
            ctx.render("sales_quotations.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", "Kunne ikke hente alle tilbud " + e.getMessage());
            ctx.render("sales_dashboard.html");
        }
    }
}