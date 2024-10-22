package edu.icet.service.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.dto.Employee;
import edu.icet.entity.EmployeeEntity;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.SuperDao;
import edu.icet.repository.custom.EmployeeDao;
import edu.icet.repository.custom.impl.EmployeeDaoImpl;
import edu.icet.service.custom.EmployeeService;
import edu.icet.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;

public class EmployeeServiceImpl implements EmployeeService {


    EmployeeDao employeeDao = DaoFactory.getInstance().getDaoType(DaoType.EMPLOYEE);

    @Override
    public boolean addEmployee(Employee employee) {
        System.out.println("e service reached");

        EmployeeEntity entity = new ModelMapper().map(employee, EmployeeEntity.class);
        return employeeDao.save(entity);

    }

    @Override
    public ObservableList<Employee> getAllEmployee() {

        ObservableList<EmployeeEntity> employeeEntities = employeeDao.searchAll();

        ObservableList<Employee> employeeList = FXCollections.observableArrayList();

        employeeEntities.forEach(employeeEntity -> {
            System.out.println("<<<<<<<<<<<<<<<<<<<< "+employeeEntity);
            employeeList.add(new ObjectMapper().convertValue(employeeEntity, Employee.class) );
        });


        return employeeList;
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        return employeeDao.update(new ObjectMapper().convertValue(employee,EmployeeEntity.class));
    }

    @Override
    public boolean deleteEmployee(String id) {
        return employeeDao.delete(id);
    }

    @Override
    public Employee searchByName(String name) {
        EmployeeEntity employeeEntity = employeeDao.search(name);

        return new ObjectMapper().convertValue(employeeEntity,Employee.class);
    }

    @Override
    public String generateEmployeeId() {
        String lastEmployeeId = employeeDao.getLatestId();
        if (lastEmployeeId==null){
            return "E0001";
        }

        int number = Integer.parseInt(lastEmployeeId.split("E")[1]);
        number++;
        return String.format("E%04d", number);
    }

    @Override
    public Employee searchByEmail(String email) {
        EmployeeEntity employeeEntity = employeeDao.searchByEmail(email);
        return new ObjectMapper().convertValue(employeeEntity,Employee.class);
    }


}
