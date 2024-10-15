package edu.icet.service;

import edu.icet.service.custom.impl.CustomerServiceImpl;
import edu.icet.service.custom.impl.EmployeeServiceImpl;
import edu.icet.service.custom.impl.SupplierServiceImpl;
import edu.icet.util.ServiceType;

public class ServiceFactory {

    private ServiceFactory(){}

    public static ServiceFactory instance;

    public static ServiceFactory getInstance(){
        return instance==null ? instance=new ServiceFactory():instance;
    }

    public <T extends SuperService>T getServiceType(ServiceType type){
        switch (type){
            case EMPLOYEE :return (T) new EmployeeServiceImpl();
            case CUSTOMER: return (T)new CustomerServiceImpl();
            case SUPPLIER:return (T)new SupplierServiceImpl();
        }
        return null;
    }

}
