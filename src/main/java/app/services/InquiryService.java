package app.services;

import app.entities.Inquiry;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.InquiryMapper;

import java.util.List;

public class InquiryService {

    private final InquiryMapper inquiryMapper;

    public InquiryService(InquiryMapper inquiryMapper){
        this.inquiryMapper =inquiryMapper;
    }

    public Inquiry getInquires (int inquiryId) {

        Inquiry inquiry = inquiryMapper.findById(inquiryId);

        if (inquiry == null){
            throw new RuntimeException("Forspørgelse ikke fundet");
        }
        return inquiry;
    }

    public List<Inquiry> getAllInquiries() {
        return inquiryMapper.findAll();
    }

    public void handleInquiry(Inquiry inquiry){
        //forretnings logik put her ind.

        validateInquiry(inquiry);

        inquiryMapper.insertInquiry(inquiry);
    }

    public void deleteInquiry(int inquiryId){
        Inquiry inquiry = inquiryMapper.findById(inquiryId);

        if (inquiry == null){
            throw new RuntimeException("Kan ikke slætte. Forspørgelse ikke fundet");
        }
        inquiryMapper.deleteInquiry(inquiryId);
    }

    private void validateInquiry(Inquiry inquiry){

        if (inquiry.getEmail() == null || inquiry.getEmail().isBlank()){
            throw new RuntimeException("Email påkrævet");
        }
        if (inquiry.getMessage() == null || inquiry.getMessage().isBlank()){
            throw new RuntimeException("Meddelse er krævet");
        }
    }


}
