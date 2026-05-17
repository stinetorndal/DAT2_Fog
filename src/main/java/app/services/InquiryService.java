package app.services;

import app.entities.Inquiry;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.InquiryMapper;

import java.util.ArrayList;
import java.util.List;

public class InquiryService {

    private InquiryMapper inquiryMapper = new InquiryMapper();

    public void handleInquiry(Inquiry inquiry, ConnectionPool connectionPool) throws DatabaseException{
        inquiryMapper.saveInquiry(inquiry, connectionPool);
    }

    public List<Inquiry> getAllInquiries(ConnectionPool connectionPool) throws DatabaseException {
        return inquiryMapper.getAllInquiries(connectionPool);
    }

    public Inquiry getInquiryById(int inquiryId, ConnectionPool connectionPool) throws DatabaseException {
        return inquiryMapper.getInquiryBy(inquiryId, connectionPool);
    }
}
