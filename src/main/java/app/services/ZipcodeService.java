package app.services;

import app.entities.Inquiry;
import app.entities.Zipcode;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.ZipcodeMapper;

public class ZipcodeService {
    private ZipcodeMapper zipcodeMapper = new ZipcodeMapper();

    public Zipcode getZipcodeObject(int zipcode, ConnectionPool connectionPool) throws DatabaseException {
        return zipcodeMapper.getZipcodeAndCity(zipcode, connectionPool);
    }

}
