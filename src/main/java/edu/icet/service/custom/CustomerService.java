package edu.icet.service.custom;

import edu.icet.dto.Customer;
import edu.icet.dto.Employee;
import edu.icet.dto.Product;
import edu.icet.service.SuperService;
import javafx.collections.ObservableList;

public interface CustomerService extends SuperService {
    boolean addCustomer(Customer customer);

    ObservableList<Customer> getAllCustomer();

    boolean updateCustomer(Customer customer);

    boolean deleteCustomer(String id);

    Customer searchByName (String name);

    Customer searchById (String id);

    String generateCustomerId();
}
