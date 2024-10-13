package edu.icet.repository;

import edu.icet.repository.custom.impl.CustomerDaoImpl;
import edu.icet.repository.custom.impl.EmployeeDaoImpl;
import edu.icet.util.DaoType;

public class DaoFactory {
    private DaoFactory(){}

    private static DaoFactory instance;
    public static DaoFactory getInstance(){
        return null==instance?instance=new DaoFactory():instance;
    }

    public <T extends SuperDao>T getDaoType(DaoType type){
        switch (type){
            case EMPLOYEE :return (T) new EmployeeDaoImpl();
            case CUSTOMER:return (T)new CustomerDaoImpl();
        }
        return null;
    }
}
