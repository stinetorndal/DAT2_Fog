package app.services;

import app.entities.Inquiry;
import app.entities.Material;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.util.List;

public class CalculateTotalPrice {

    private CalculateBom calculateBom = new CalculateBom();

    public double calculatePrice(Inquiry inquiry, ConnectionPool connectionPool) throws DatabaseException {
        List<Material> bom = calculateBom.calculateCarport(inquiry, connectionPool);

        double carportPrice = 0;

        for(Material m : bom) {
            carportPrice += m.getPricePerUnit();
        }

        return carportPrice;
    }
}
