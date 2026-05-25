package app.services;

import app.entities.Customer;
import app.entities.Zipcode;
import app.exceptions.DatabaseException;
import app.services.CustomerService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerServiceTest {

    @Test
    //Testen skal vise om der kommer DatabaseException hvis man skriver ukorrekt emailadresse
    void createCustomerWithInvalidEmail (){
        //Arrange
        CustomerService customerService = new CustomerService();
        Zipcode testZipcode = new Zipcode (4400, "Kalundborg");
        Customer incorrectCustomer = new Customer(0, "Herr", "Test", "Test Alle 1", testZipcode, "jeg har glemtsnabela");

        //Assert and Act
        try {
            customerService.createCustomer(incorrectCustomer, null);
            //Hvis testen når hertil, kaster koden IKKE fejl
            assertTrue(false, "Metode burde ikke kaste fejl, email er med vilje lavet forkert");
        } catch (DatabaseException e) {
            assertEquals ("Emailadresse SKAL indeholde @", e.getMessage());
        }

    }
}
