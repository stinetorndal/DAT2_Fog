package app.entities;

import app.enums.QuoteStatus;

public class Quote {

    private int quotationId;
    private int inquiryId;
    private int salespersonId;
    private double price;
    private QuoteStatus status;
    private int version;

    private Integer previousQuoteId;

    public Quote(int inquiryId, int salespersonId, double price) {
        this.inquiryId = inquiryId;
        this.salespersonId = salespersonId;
        this.price = price;
        this.status = QuoteStatus.PENDING;
        this.version = 1;
    }

    public Quote(int quotationId,int inquiryId, int salespersonId, double price, QuoteStatus status, int version, Integer previousQuoteId) {

        this.quotationId = quotationId;
        this.inquiryId = inquiryId;
        this.salespersonId = salespersonId;
        this.price = price;
        this.status = status;
        this.version = version;
        this.previousQuoteId = previousQuoteId;
    }

    //creating new version of quote

    public Quote createNewVersion(double newPrice){
        Quote newQuote = new Quote(this.inquiryId, this.salespersonId,newPrice);

        newQuote.setVersion(this.version +1);

        newQuote.setStatus(QuoteStatus.UPDATED);

        newQuote.setPreviousQuoteId(this.quotationId);

        return newQuote;
    }






    public int getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(int quotationId) {
        this.quotationId = quotationId;
    }

    public int getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(int inquiryId) {
        this.inquiryId = inquiryId;
    }

    public int getSalespersonId() {
        return salespersonId;
    }

    public void setSalespersonId(int salespersonId) {
        this.salespersonId = salespersonId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public QuoteStatus getStatus() {
        return status;
    }

    public void setStatus(QuoteStatus status) {
        this.status = status;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Integer getPreviousQuoteId() {
        return previousQuoteId;
    }

    public void setPreviousQuoteId(Integer previousQuoteId) {
        this.previousQuoteId = previousQuoteId;
    }

}
