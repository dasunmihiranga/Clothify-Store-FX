package edu.icet.repository.custom.impl;

import edu.icet.entity.CustomerEntity;
import edu.icet.entity.EmployeeEntity;
import edu.icet.repository.custom.CustomerDao;
import edu.icet.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(CustomerEntity customer) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.persist(customer);
        session.getTransaction().commit();
        session.close();
        return false;
    }

    @Override
    public ObservableList<CustomerEntity> searchAll() {
        Session session = HibernateUtil.getSession();
        ObservableList<CustomerEntity> customerEntityList = FXCollections.observableArrayList();

        try {
            session.getTransaction().begin();
            List<CustomerEntity> list = session.createQuery("FROM CustomerEntity", CustomerEntity.class).getResultList();
            session.getTransaction().commit();

            list.forEach(customerEntity -> {
                System.out.println("--------> "+customerEntity);
                customerEntityList.add(customerEntity);
            });

            return customerEntityList;  // Return the populated list
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return FXCollections.observableArrayList(); // Return an empty list in case of failure
        } finally {
            session.close();  // Ensure session is always closed
        }
    }

    @Override
    public boolean update(CustomerEntity customer) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query=session.createQuery("UPDATE CustomerEntity SET name =:name,address =:address,email =:email WHERE id =:id");

        query.setParameter("name",customer.getName());
        query.setParameter("address",customer.getAddress());
        query.setParameter("email",customer.getEmail());
        query.setParameter("id",customer.getId());

        int i =query.executeUpdate();
        session.getTransaction().commit();
        session.close();

        return i>0;
    }

    @Override
    public boolean delete(String id) {
        Session session =HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("DELETE FROM CustomerEntity WHERE id=:id");
        query.setParameter("id",id);
        int i =query.executeUpdate();
        session.getTransaction().commit();
        session.close();

        return i>0;
    }

    @Override
    public CustomerEntity search(String name) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("FROM CustomerEntity WHERE name=:name");
        query.setParameter("name",name);

        CustomerEntity customerEntity = (CustomerEntity) query.uniqueResult();
        session.close();

        return customerEntity;
    }
}
