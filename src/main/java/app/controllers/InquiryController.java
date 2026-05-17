package app.controllers;

import app.entities.Customer;
import app.entities.Inquiry;
import app.entities.Zipcode;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.services.CustomerService;
import app.services.InquiryService;
import io.javalin.Javalin;
import io.javalin.http.Context;


public class InquiryController {

    private InquiryService inquiryService = new InquiryService();

    public void addRoutes(Javalin app, ConnectionPool connectionPool) {
        InquiryController inquiryController = new InquiryController();

        app.get("/inquiry", ctx -> ctx.render("index.html"));
        app.post("/submit-inquiry", ctx -> inquiryController.createInquiry(ctx, connectionPool));
    }

    private void createInquiry(Context ctx, ConnectionPool connectionPool) {

        int length = getLength(ctx);
        int width = getWidth(ctx);
        int shedLength = getShedLength(ctx);
        int shedWidth = getShedWidth(ctx);

        try {
            int customerId = handleCustomer(ctx, connectionPool);
            Inquiry newInquiry = new Inquiry(customerId, length, width, shedLength, shedWidth);
            inquiryService.handleInquiry(newInquiry, connectionPool);
            ctx.sessionAttribute("currentInquiry", newInquiry);
            ctx.render("confirmation");
        } catch (DatabaseException e) {
            //"message" fra th-reference i html - her får vi system-fejlmeddelelse
            //TODO check hvor i html den er - skal måske ændres / opdateres?
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

    Zipcode zipcodeObject = new Zipcode(zipcode, "");
    Customer newCustomer = new Customer(0, firstName, lastName, address, zipcodeObject, email);

    //Send videre til service
    CustomerService customerService = new CustomerService();
    return customerService.createCustomer(newCustomer, connectionPool);
       //Denne skal ind, hvis vi laver regex-check her. Og ellers klarer validator det
    // throw new DatabaseException("Email-adressen er ikke gyldig.");

    }
}