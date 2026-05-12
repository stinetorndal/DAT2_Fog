package app.entities;

public class Zipcode {
    private int Zipcode;
    private String city;



    public Zipcode(int zipcode, String city) {
        Zipcode = zipcode;
        this.city = city;
    }

    public int getZipcode() {
        return Zipcode;
    }

    public void setZipcode(int zipcode) {
        Zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
