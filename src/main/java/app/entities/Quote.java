package app.entities;

public class Quote {

    private int quotationId;
    private int inquiryId;
    private int salespersonId;
    private double price;
    private String status;
    private int version;

    public Quote(int inquiryId, int salespersonId, double price) {
        this.inquiryId = inquiryId;
        this.salespersonId = salespersonId;
        this.price = price;
        this.status = "pending";
        this.version = 1;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
