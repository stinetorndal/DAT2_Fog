package app.services;

import app.entities.Salesperson;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.SalespersonMapper;


public class SalespersonService {

    private SalespersonMapper salespersonMapper = new SalespersonMapper();

    public Salesperson login(String email, String password, ConnectionPool connectionPool) throws DatabaseException {
        return salespersonMapper.login(email, password, connectionPool);
    }
}
