package app.entities;

public class Quote {
    private int quotationId;
    private int inquiryId;
    private int salespersonId;
    private int length;
    private int width;
    private double price;
    private String status;
    private int quotationNumber;
    private int version;

    // Ekstra felt til brug i Thymeleaf-tabellen
    private String customerName;

    public Quote(int inquiryId, int salespersonId, int length, int width, double price) {
        this.inquiryId = inquiryId;
        this.salespersonId = salespersonId;
        this.length = length;
        this.width = width;
        this.price = price;
        this.status = "pending";
        this.version = 1;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
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

    public int getQuotationNumber() {
        return quotationNumber;
    }

    public void setQuotationNumber(int quotationNumber) {
        this.quotationNumber = quotationNumber;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    public String getCustomerName() {
        return customerName; }
}