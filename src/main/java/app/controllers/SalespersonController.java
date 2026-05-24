package app.controllers;

import app.entities.Salesperson;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.services.SalespersonService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class SalespersonController {

    private SalespersonService salespersonService = new SalespersonService();

    public void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.post("/login", ctx -> login(ctx, connectionPool));
        app.get("/logout", ctx -> logout(ctx));
    }

    //En almindelig attribute lever kun i meget kort tid. Session attribute lever lige så længe som sessionen.

    private void login(Context ctx, ConnectionPool connectionPool) {

        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try {
            //Kald metode fra service, som returnerer sælger-objekt (fra mapper)
            Salesperson salesperson = salespersonService.login(email, password, connectionPool);

            //Gem sælgeren i sessionen.
            ctx.sessionAttribute("currentUser", salesperson);

            //Send videre til sælgersiden.
            ctx.render("sales.html");
        } catch (DatabaseException e) { //Hvis sælgeren ikke findes i databasen, ryger man herned.
            //Viser en fejlmeddelelse i frontend, hvis vi kobler den op på en th:text="${message}" i html.
            ctx.attribute("message", e.getMessage());
            //Går tilbage til login-siden.
            ctx.render("login.html");
        }
    }

    private void logout(Context ctx) {
        ctx.req().getSession().invalidate(); //"Hent requesten → hent sessionen → slet den". invalidate() sletter hele sessionen, hvilket man gerne vil have, når man logger ud.
        ctx.redirect("/"); //sender brugeren tilbage til forsiden. redirect gør, at man kommer tilbage til forsiden, som om det vitterligt var forsiden, man ramte. redirect bruges kun i særlige tilfælde.
    }
}
