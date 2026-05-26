package app.services;

import app.entities.Inquiry;
import app.entities.Material;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.util.List;

public class CalculateTotalPrice {

    private int length;
    private int width;

    public CalculateTotalPrice(int length, int width) {
        this.length = length;
        this.width = width;
    }

    private CalculateBom calculateBom = new CalculateBom(length, width);

    public double calculatePrice(int length, int width, ConnectionPool connectionPool) throws DatabaseException {
        List<Material> bom = calculateBom.calculateCarport(length, width, connectionPool);

        double carportPrice = 0;

        for(Material m : bom) {
            carportPrice += m.getPricePerUnit();
        }

        return carportPrice;
    }
}
