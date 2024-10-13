package edu.icet.service.custom;

import edu.icet.dto.Employee;
import edu.icet.service.SuperService;
import javafx.collections.ObservableList;

public interface EmployeeService extends SuperService {

    boolean addEmployee(Employee employee);

    ObservableList<Employee>getAllEmployee();

    boolean updateEmployee(Employee employee);

    boolean deleteEmployee(String id);

    Employee searchByName (String name);


}
