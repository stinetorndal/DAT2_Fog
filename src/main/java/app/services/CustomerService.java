package app.services;

import app.entities.Customer;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

public class CustomerService {
    //private CustomerMapper customerMapper = new CustomerMapper();

     /* Udkommenteret fordi jeg er nødt til at returnere et dummynummer for at teste min InquiryMapper
    public int createCustomer (Customer customer, ConnectionPool connectionPool) throws DatabaseException {
         return customerMapper.saveCustomer(customer, connectionPool);

      */
     public int createCustomer(Customer customer, ConnectionPool connectionPool) throws DatabaseException {
         //TODO: CustomerMapper.saveCustomer skal rettes til at returnere det genererede ID (int).
         if (!customer.getEmail().contains("@")){
             throw new DatabaseException("Emailadresse SKAL indeholde @");
         }

         return 1;

}
}

