package edu.icet.repository.custom.impl;

import edu.icet.entity.EmployeeEntity;
import edu.icet.entity.OrderDetailEntity;
import edu.icet.entity.OrderEntity;
import edu.icet.entity.ProductEntity;
import edu.icet.repository.custom.OrderDao;
import edu.icet.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public boolean save(OrderEntity order) {

        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        session.persist(order);
        session.getTransaction().commit();
        session.close();
        return true;
    }


    @Override
    public ObservableList<OrderEntity> searchAll() {
        Session session = HibernateUtil.getSession();
        ObservableList<OrderEntity> orderEntityList = FXCollections.observableArrayList();

        try {
            session.getTransaction().begin();
            List<OrderEntity> list = session.createQuery("FROM OrderEntity", OrderEntity.class).getResultList();
            session.getTransaction().commit();

            list.forEach(orderEntity -> {
                System.out.println("--------> "+orderEntity);
                orderEntityList.add(orderEntity);
            });

            return orderEntityList;  // Return the populated list

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return FXCollections.observableArrayList(); // Return an empty list in case of failure
        } finally {
            session.close();  // Ensure session is always closed
        }
    }

    @Override
    public boolean update(OrderEntity order) {
        return false;
    }

    @Override
    public boolean delete(String s) {
        return false;
    }

    @Override
    public OrderEntity search(String name) {
       return null;
    }

    @Override
    public String getLatestId() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("SELECT id FROM OrderEntity ORDER BY id DESC LIMIT 1");
        String id = (String) query.uniqueResult();
        session.close();
        return id;
    }

    @Override
    public OrderEntity searchById(String id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("FROM OrderEntity WHERE id=:id");
        query.setParameter("id",id);

        OrderEntity orderEntity = (OrderEntity) query.uniqueResult();
        session.close();

        return orderEntity;
    }


}
