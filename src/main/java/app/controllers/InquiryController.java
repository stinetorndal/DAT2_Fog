package app.controllers;

import app.entities.Customer;
import app.entities.Inquiry;
import app.entities.Zipcode;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.services.CustomerService;
import app.services.EmailService;
import app.services.InquiryService;
import app.services.PdfService;
import io.javalin.Javalin;
import io.javalin.http.Context;


public class InquiryController {

    private InquiryService inquiryService = new InquiryService();
    private PdfService pdfService = new PdfService();
    private EmailService emailService = new EmailService();

    public void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/inquiry", ctx -> ctx.render("index.html"));
        app.post("/submit-inquiry", ctx -> createInquiry(ctx, connectionPool));
        //Kalder metoder, der sender pdf tilbage til browser efter generering
        app.get("/download-pdf", ctx -> downloadPdf(ctx));
    }

        private void createInquiry(Context ctx, ConnectionPool connectionPool) {
        int length = getLength(ctx);
        int width = getWidth(ctx);
        int shedLength = getShedLength(ctx);
        int shedWidth = getShedWidth(ctx);

        try {
            //Få customerId fra DB
            int customerId = handleCustomer(ctx, connectionPool);
            //Inquiry kan nu knyttes til kunden med ID
            Inquiry newInquiry = new Inquiry(customerId, length, width, shedLength, shedWidth);
            inquiryService.handleInquiry(newInquiry, connectionPool);

            Customer customerInquiryPdf = getCustomerFromFormParam(ctx);
            byte[] pdfBytes = pdfService.generateInquiryPdf (customerInquiryPdf, newInquiry);

            ctx.sessionAttribute("currentInquiry", newInquiry);
            ctx.sessionAttribute("pdfBytes", pdfBytes);
            ctx.attribute("customer", customerInquiryPdf);
            ctx.attribute("inquiry", newInquiry);

            sendConfirmationEmail(customerInquiryPdf);
            ctx.render("inquiry_to_pdf.html");
            
        } catch (DatabaseException e) {
            //"message" fra th-reference i html - her får vi system-fejlmeddelelse
            //TODO check hvor i html den er - skal måske ændres / opdateres?
            ctx.attribute("message", e.getMessage());
            ctx.render("index.html");
        }
    }

    private int handleCustomer(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        Customer newCustomer = getCustomerFromFormParam(ctx);

        //Send videre til service
        CustomerService customerService = new CustomerService();
        return customerService.createCustomer(newCustomer, connectionPool);
    }

    //hjælpemetode til handleCustomer og createInquiry med formParam
    private Customer getCustomerFromFormParam(Context ctx) {
        String firstName = ctx.formParam("fornavn");
        String lastName = ctx.formParam("efternavn");
        String address = ctx.formParam("adresse");
        int zipcode = Integer.parseInt(ctx.formParam("postnummer"));
        String email = ctx.formParam("email");

        Zipcode zipcodeObject = new Zipcode(zipcode);
        Customer customer = new Customer(firstName, lastName, address, zipcodeObject, email);
        return customer;
    }

    private void downloadPdf (Context ctx) {
        byte[] pdfBytes = ctx.sessionAttribute("pdfBytes");
        if (pdfBytes!= null) {
            //Fortæller browser dette er en pdf-fil
            ctx.contentType("application/pdf");
            //Giver browser besked på download + definerer navn
            ctx.header("Content-Disposition", "attachment; filename=carport-forespoergsel.pdf");
            //Send bytes ud i browser
            ctx.result(pdfBytes);
        } else {
            //Hvis session er udløbet = send bruger tilbage til index.html
            ctx.attribute("message", "PDF-session er udløbet");
            ctx.render("index.html");
        }
    }

    //Hjælpemetode til bodyText i email.
    //TODO bør laves som DTO
    private void sendConfirmationEmail (Customer customer){
        String subject = "Bekræftelse på din carport-forespørgsel";
        //Body-tekst
        String bodyText = "Kære " + customer.getFirstname() + ",\n\n"
        + "Tak for din forespørgsel hos Fog\n"
        + "Vi har modtaget dine specifikationer og går i gang med at beregne et tilbud til dig.\n\n"
        + "Venlig hilsen, \nFog Byggecenter";

        //Uddelegér til emailservice-forsendelse
        emailService.sendEmail(customer.getEmail(), subject, bodyText);

    }
    private int getLength(Context ctx) {
        return Integer.parseInt(ctx.formParam("længde"));
    }

    private int getWidth(Context ctx) {
        return Integer.parseInt(ctx.formParam("bredde"));
    }

    //Hent data fra formular. Citatnavne skal matche html-navne
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

}