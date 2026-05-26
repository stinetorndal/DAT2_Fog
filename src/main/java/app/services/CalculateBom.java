package app.services;

import app.entities.Material;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.util.ArrayList;
import java.util.List;

public class CalculateBom {

    private int length;
    private int width;

    public CalculateBom(int length, int width) {
        this.length = length;
        this.width = width;
    }

    Calculator calculator = new Calculator(length, width);

    public List<Material> calculateCarport(int length, int width, ConnectionPool connectionPool) throws DatabaseException {
        List<Material> bom = new ArrayList<>();

        // Tilføjer stolpe-objekter til stykliste.
        bom.addAll(calculator.calculatePosts(length, connectionPool));

        // Tilføjer rem-objekter til stykliste.
        bom.addAll(calculator.calculateBeams(length, connectionPool));

        // Tilføjer spær-objekter til stykliste.
        bom.addAll(calculator.calculateRafts(length, width, connectionPool));
        return bom;
    }
}
