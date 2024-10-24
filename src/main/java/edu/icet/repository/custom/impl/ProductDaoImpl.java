package edu.icet.repository.custom.impl;

import edu.icet.entity.ProductEntity;
import edu.icet.entity.SupplierEntity;
import edu.icet.repository.custom.ProductDao;
import edu.icet.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public boolean save(ProductEntity producto) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.persist(producto);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public ObservableList<ProductEntity> searchAll() {
        Session session = HibernateUtil.getSession();
        ObservableList<ProductEntity> productEntityList = FXCollections.observableArrayList();

        try {
            session.getTransaction().begin();
            List<ProductEntity> list = session.createQuery("FROM ProductEntity", ProductEntity.class).getResultList();
            session.getTransaction().commit();

            list.forEach(productEntity-> {
                System.out.println(productEntity);
                productEntityList.add(productEntity);
            });

            return productEntityList;  // Return the populated list
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return FXCollections.observableArrayList(); // Return an empty list in case of failure
        } finally {
            session.close();  // Ensure session is always closed
        }
    }

    @Override
    public boolean update(ProductEntity product) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query=session.createQuery("UPDATE ProductEntity SET name =:name,size =:size,qty =:qty,supplierId =:supplierId,unitPrice =:unitPrice,category =:category WHERE productId =:productId");

        query.setParameter("name",product.getName());
        query.setParameter("size",product.getSize());
        query.setParameter("qty",product.getQty());
        query.setParameter("supplierId",product.getSupplier().getId());
        query.setParameter("unitPrice",product.getUnitPrice());
        query.setParameter("category",product.getCategory());
        query.setParameter("productId",product.getProductId());

        int i =query.executeUpdate();
        session.getTransaction().commit();
        session.close();

        return i>0;

    }

    @Override
    public boolean delete(String productId) {
        Session session =HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("DELETE FROM ProductEntity WHERE productId=:productId");
        query.setParameter("productId",productId);
        int i =query.executeUpdate();
        session.getTransaction().commit();
        session.close();

        return i>0;
    }

    @Override
    public ProductEntity search(String name) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("FROM ProductEntity WHERE name=:name");
        query.setParameter("name",name);

        ProductEntity productEntity = (ProductEntity) query.uniqueResult();
        session.close();

        return productEntity;
    }

    @Override
    public String getLatestId() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("SELECT productId FROM ProductEntity ORDER BY productId DESC LIMIT 1");
        String id = (String) query.uniqueResult();
        session.close();
        return id;
    }

    @Override
    public ProductEntity searchById(String productId) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("FROM ProductEntity WHERE productId=:productId");
        query.setParameter("productId",productId);

        ProductEntity productEntity = (ProductEntity) query.uniqueResult();
        session.close();

        return productEntity;
    }


}
