package app.entities;

public class Customer {
    private int costumerId;
    private String firstname;
    private String lastname;
    private String address;
    private int zipcode;
    private String email;

    public Customer(int costumerId, String firstname, String lastname, String address, int zipcode, String email) {
        this.costumerId = costumerId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.zipcode = zipcode;
        this.email = email;
    }

    public int getCostumerId() {
        return costumerId;
    }

    public void setCostumerId(int costumerId) {
        this.costumerId = costumerId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
