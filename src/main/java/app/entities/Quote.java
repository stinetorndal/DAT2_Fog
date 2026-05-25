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

    public Quote(int quotationId, int inquiryId, int salespersonId, int length, int width, double price, String status, int quotationNumber, int version, String customerName) {
        this.quotationId = quotationId;
        this.inquiryId = inquiryId;
        this.salespersonId = salespersonId;
        this.length = length;
        this.width = width;
        this.price = price;
        this.status = status;
        this.quotationNumber = quotationNumber;
        this.version = version;
        this.customerName = customerName;
    }


    public int getQuotationId() {
        return quotationId; }

    public int getInquiryId() {
        return inquiryId; }

    public int getSalespersonId() {
        return salespersonId; }

    public int getLength() {
        return length; }

    public int getWidth() {
        return width; }

    public double getPrice() {
        return price; }

    public String getStatus() {
        return status; }

    public int getQuotationNumber() {
        return quotationNumber; }

    public int getVersion() {
        return version; }

    public String getCustomerName() {
        return customerName; }
}