package app.services;

import app.entities.Customer;
import app.entities.Zipcode;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.CustomerMapper;
import app.persistence.QuoteMapper;

public class CustomerService {

    private CustomerMapper customerMapper = new CustomerMapper();
    private QuoteMapper quoteMapper = new QuoteMapper();

    public int createCustomer (Customer customer, ConnectionPool connectionPool) throws DatabaseException {
         return customerMapper.saveCustomer(customer, connectionPool);
     }

    public Customer getCustomerById(int customerId, ConnectionPool connectionPool) throws DatabaseException {
        return customerMapper.getCustomerById(customerId, connectionPool);
    }

    public Customer getCustomerByQuote(int quoteId, ConnectionPool connectionPool) throws DatabaseException {
        return quoteMapper.getCustomerByQuote(quoteId, connectionPool);
    }
}