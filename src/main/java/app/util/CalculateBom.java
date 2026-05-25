package app.util;

import app.entities.Inquiry;
import app.entities.Material;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.util.ArrayList;
import java.util.List;

public class CalculateBom {

    Calculator calculator = new Calculator();

    public List<Material> calculateCarport(Inquiry inquiry, ConnectionPool connectionPool) throws DatabaseException {
        List<Material> bom = new ArrayList<>();

        // Tilføjer stolpe-objekter til stykliste.
        bom.addAll(calculator.calculatePosts(inquiry, connectionPool));

        // Tilføjer rem-objekter til stykliste.
        bom.addAll(calculator.calculateBeam(inquiry, connectionPool));

        // Tilføjer spær-objekter til stykliste.
        bom.addAll(calculator.calculateRafts(inquiry, connectionPool));
        return bom;
    }
}
