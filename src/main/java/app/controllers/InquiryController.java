package app.controllers;

import app.entities.Inquiry;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.services.InquiryService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;


public class InquiryController {

    private InquiryService inquiryService = new InquiryService();

    public void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/inquiry", ctx -> ctx.render("index.html"));
        app.post("/submit-inquiry", ctx -> createInquiry(ctx, connectionPool));
        app.get("/sales/all-inquiries", ctx -> showAllInquiries(ctx, connectionPool));
        app.get("/sales/inquiry/{id}", ctx -> showInquiry(ctx, connectionPool));
        // Krølleparenteserne er Javalins syntaks for en path parameter.
        // {id} er en variabel del af URL'en, som hentes med ctx.pathParam("id").
    }

    private void createInquiry(Context ctx, ConnectionPool connectionPool) {

        int length = getLength(ctx);
        int width = getWidth(ctx);
        int shedLength = getShedLength(ctx);
        int shedWidth = getShedWidth(ctx);

        try {
            int customerId = handleCustomer(ctx, connectionPool);
            Inquiry newInquiry = new Inquiry(1, length, width, shedLength, shedWidth);
            inquiryService.handleInquiry(newInquiry, connectionPool);
            ctx.sessionAttribute("currentInquiry", newInquiry);
            ctx.render("confirmation");
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("index.html");
        }
            }

private int getLength(Context ctx) {
    return Integer.parseInt(ctx.formParam("længde"));
}

private int getWidth(Context ctx) {
    return Integer.parseInt(ctx.formParam("bredde"));
}

//Hent data fra formular. Citatnavne skal matche html-navne
//Brug radiobuttons til nedenstående!!! Den med <input type"'radio>
private int getShedLength(Context ctx) {
    String hasShed = ctx.formParam("skur_ja_nej");
    if ("ja".equals(hasShed)) {
        return Integer.parseInt(ctx.formParam("skur_længde"));
    }
    return 0;
}

private int getShedWidth(Context ctx) {
        String hasShed = ctx.formParam("skur_ja_nej");
        if ("ja".equals(hasShed)) {
            return Integer.parseInt(ctx.formParam("skur_bredde"));
        }
        return 0;
}
private int handleCustomer(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
    String firstName = ctx.formParam("fornavn");
    String lastName = ctx.formParam("efternavn");
    String address = ctx.formParam("adresse");
    int zipcode = Integer.parseInt(ctx.formParam("postnummer"));
    String email = ctx.formParam("email");
    if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
        throw new DatabaseException("Email-adressen er ikke gyldig.");
    }
        CustomerService customerService = new CustomerService();
        return customerService.createCustomer(firstName, lastName, address, zipcode, email, connectionPool);
    }

    public void showAllInquiries(Context ctx, ConnectionPool connectionPool) {
        try {
            List<Inquiry> allInquiries = inquiryService.getAllInquiries(connectionPool);

            ctx.attribute("allInquiries", allInquiries);
            ctx.render("all-inquiries.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("sales.html");
        }
    }

    public void showInquiry(Context ctx, ConnectionPool connectionPool) {
        try {
            //Id hentes fra URL'en. I html skal hver forespørgsel gøres klikbar med et link, der indeholder id'et.
            //pathParam returnerer en String. Derfor parser vi, så f.eks. "5" bliver lavet om til 5.
            int inquiryId = Integer.parseInt(ctx.pathParam("id"));
            Inquiry inquiry = inquiryService.getInquiryById(inquiryId, connectionPool);

            ctx.attribute("inquiry", inquiry);
            ctx.render("inquiry.html");
        } catch (DatabaseException | NumberFormatException e) { //NumberFormatException er med her, fordi vi i try-blokken
            // forsøger at parse til en int. Hvis url'en f.eks. indeholder "abc" i stedet for "5",
            // så kan den ikke parses/konverteres til en int, og det vil give en fejl/exception
            // (som vi selvfølgelig skal tage os af 😄).
            ctx.attribute("message", e.getMessage());
            ctx.render("all-inquiries.html");
        }
    }
}










