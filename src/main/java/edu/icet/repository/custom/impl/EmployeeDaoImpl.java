package edu.icet.repository.custom.impl;

import edu.icet.entity.EmployeeEntity;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.EmployeeDao;
import edu.icet.util.DaoType;
import edu.icet.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;

import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {


    @Override
    public boolean save(EmployeeEntity employee) {
        System.out.println("e repository reach ");

        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        session.persist(employee);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public ObservableList<EmployeeEntity> searchAll() {
        Session session = HibernateUtil.getSession();
        ObservableList<EmployeeEntity> employeeEntityList = FXCollections.observableArrayList();

        try {
            session.getTransaction().begin();
            List<EmployeeEntity> list = session.createQuery("FROM EmployeeEntity", EmployeeEntity.class).getResultList();
            session.getTransaction().commit();

            list.forEach(employeeEntity -> {
                System.out.println("--------> "+employeeEntity);
                employeeEntityList.add(employeeEntity);
            });

            return employeeEntityList;  // Return the populated list
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return FXCollections.observableArrayList(); // Return an empty list in case of failure
        } finally {
            session.close();  // Ensure session is always closed
        }
    }

    @Override
    public boolean update(EmployeeEntity employee) {

        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query=session.createQuery("UPDATE EmployeeEntity SET name =:name,address =:address,email =:email,password =:password WHERE id =:id");

        query.setParameter("name",employee.getName());
        query.setParameter("address",employee.getAddress());
        query.setParameter("email",employee.getEmail());
        query.setParameter("password",employee.getPassword());
        query.setParameter("id",employee.getId());

        int i =query.executeUpdate();
        session.getTransaction().commit();
        session.close();

        return i>0;
    }

    @Override
    public boolean delete(String id) {
        Session session =HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("DELETE FROM EmployeeEntity WHERE id=:id");
        query.setParameter("id",id);
        int i =query.executeUpdate();
        session.getTransaction().commit();
        session.close();

        return i>0;
    }

    @Override
    public EmployeeEntity search(String name) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("FROM EmployeeEntity WHERE name=:name");
        query.setParameter("name",name);

        EmployeeEntity employeeEntity = (EmployeeEntity) query.uniqueResult();
        session.close();

        return employeeEntity;
    }

    @Override
    public String getLatestId() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("SELECT id FROM EmployeeEntity ORDER BY id DESC LIMIT 1");
        String id = (String) query.uniqueResult();
        session.close();
        return id;
    }


}
