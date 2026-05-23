package app.services;

import app.entities.Inquiry;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.InquiryMapper;

import java.util.List;

public class InquiryService {

    private InquiryMapper inquiryMapper = new InquiryMapper();

    public void handleInquiry(Inquiry inquiry, ConnectionPool connectionPool) throws DatabaseException{
       //Sætter automatisk dato på forespørgsel
        inquiry.setDate(java.time.LocalDateTime.now());
        inquiryMapper.saveInquiry(inquiry, connectionPool);
    }
}
