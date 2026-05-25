package app.entities;

public class Zipcode {
    private int zipcode;
    private String city;


    public Zipcode(int zipcode, String city) {
        this.zipcode = zipcode;
        this.city = city;
    }

    public Zipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
