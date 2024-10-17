package edu.icet.service.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.dto.Customer;
import edu.icet.dto.Employee;
import edu.icet.dto.Product;
import edu.icet.entity.CustomerEntity;
import edu.icet.entity.EmployeeEntity;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.CustomerDao;
import edu.icet.repository.custom.EmployeeDao;
import edu.icet.service.custom.CustomerService;
import edu.icet.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;

public class CustomerServiceImpl implements CustomerService {
    CustomerDao customerDao = DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);
    @Override
    public boolean addCustomer(Customer customer) {
        CustomerEntity entity =  new ObjectMapper().convertValue(customer, CustomerEntity.class);
        return customerDao.save(entity);

    }

    @Override
    public ObservableList<Customer> getAllCustomer() {

        ObservableList<CustomerEntity> customerEntities = customerDao.searchAll();

        ObservableList<Customer> customersList = FXCollections.observableArrayList();

        customerEntities.forEach(customerEntity -> {
            System.out.println("<<<<<<<<<<<<<<<<<<<< "+customerEntity);
            customersList.add(new ObjectMapper().convertValue(customerEntity, Customer.class) );
        });


        return customersList;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return customerDao.update(new ObjectMapper().convertValue(customer,CustomerEntity.class));
    }

    @Override
    public boolean deleteCustomer(String id) {
        return customerDao.delete(id);
    }

    @Override
    public Customer searchByName(String name) {
        CustomerEntity customerEntity = customerDao.search(name);
        return new ObjectMapper().convertValue(customerEntity,Customer.class);
    }

    @Override
    public Customer searchById(String id) {
        CustomerEntity customerEntity =customerDao.searchById(id);
        return new ObjectMapper().convertValue(customerEntity, Customer.class);
    }

    @Override
    public String generateCustomerId() {
        String lastCustomerId = customerDao.getLatestId();
        if (lastCustomerId==null){
            return "C0001";
        }

        int number = Integer.parseInt(lastCustomerId.split("C")[1]);
        number++;
        return String.format("C%04d", number);
    }
}
