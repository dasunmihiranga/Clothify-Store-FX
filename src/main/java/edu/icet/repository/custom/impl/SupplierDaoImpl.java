package edu.icet.repository.custom.impl;

import edu.icet.entity.CustomerEntity;
import edu.icet.entity.SupplierEntity;
import edu.icet.repository.custom.SupplierDao;
import edu.icet.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class SupplierDaoImpl implements SupplierDao {
    @Override
    public boolean save(SupplierEntity supplier) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.persist(supplier);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public ObservableList<SupplierEntity> searchAll() {
        Session session = HibernateUtil.getSession();
        ObservableList<SupplierEntity> supplierEntityList = FXCollections.observableArrayList();

        try {
            session.getTransaction().begin();
            List<SupplierEntity> list = session.createQuery("FROM SupplierEntity", SupplierEntity.class).getResultList();
            session.getTransaction().commit();

            list.forEach(supplierEntity -> {
                System.out.println("--------> "+supplierEntity);
                supplierEntityList.add(supplierEntity);
            });

            return supplierEntityList;  // Return the populated list
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return FXCollections.observableArrayList(); // Return an empty list in case of failure
        } finally {
            session.close();  // Ensure session is always closed
        }
    }

    @Override
    public boolean update(SupplierEntity supplier) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query=session.createQuery("UPDATE SupplierEntity SET name =:name,company =:company,email =:email WHERE id =:id");

        query.setParameter("name",supplier.getName());
        query.setParameter("company",supplier.getCompany());
        query.setParameter("email",supplier.getEmail());
        query.setParameter("id",supplier.getId());

        int i =query.executeUpdate();
        session.getTransaction().commit();
        session.close();

        return i>0;
    }

    @Override
    public boolean delete(String id) {
        Session session =HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("DELETE FROM SupplierEntity WHERE id=:id");
        query.setParameter("id",id);
        int i =query.executeUpdate();
        session.getTransaction().commit();
        session.close();

        return i>0;
    }

    @Override
    public SupplierEntity search(String name) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("FROM SupplierEntity WHERE name=:name");
        query.setParameter("name",name);

        SupplierEntity supplierEntity = (SupplierEntity) query.uniqueResult();
        session.close();

        return supplierEntity;
    }

    @Override
    public String getLatestId() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("SELECT id FROM SupplierEntity ORDER BY id DESC LIMIT 1");
        String id = (String) query.uniqueResult();
        session.close();
        return id;
    }

    @Override
    public SupplierEntity searchById(String id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("FROM SupplierEntity WHERE id=:id");
        query.setParameter("id",id);

        SupplierEntity supplierEntity = (SupplierEntity) query.uniqueResult();
        session.close();

        return supplierEntity;
    }
}
