package edu.icet.service.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.dto.Product;
import edu.icet.dto.Supplier;
import edu.icet.entity.ProductEntity;
import edu.icet.entity.SupplierEntity;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.ProductDao;
import edu.icet.service.custom.ProductService;
import edu.icet.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductServiceImpl implements ProductService {
    ProductDao productDao = DaoFactory.getInstance().getDaoType(DaoType.PRODUCT);

    @Override
    public boolean addProduct(Product product) {
        ProductEntity entity = new ObjectMapper().convertValue(product, ProductEntity.class);
        return productDao.save(entity);
    }

    @Override
    public ObservableList<Product> getAllProduct() {
        ObservableList<ProductEntity> productEntities = productDao.searchAll();

        ObservableList<Product> productList = FXCollections.observableArrayList();

        productEntities.forEach(productEntity -> {
            System.out.println("<<<<<<<<<<<<<<<<<<<< "+productEntity);
            productList.add(new ObjectMapper().convertValue(productEntity, Product.class) );
        });


        return productList;
    }

    @Override
    public boolean updateProduct(Product product) {
        return productDao.update(new ObjectMapper().convertValue(product, ProductEntity.class));
    }

    @Override
    public boolean deleteProduct(String id) {
        return productDao.delete(id);
    }

    @Override
    public Product searchByName(String name) {
        ProductEntity productEntity = productDao.search(name);
        return new ObjectMapper().convertValue(productEntity, Product.class);
    }

    @Override
    public Product searchById(String id) {
        ProductEntity productEntity =productDao.searchById(id);
        return new ObjectMapper().convertValue(productEntity,Product.class);
    }

    @Override
    public String generateProductId() {
        String lastProductId = productDao.getLatestId();
        if (lastProductId==null){
            return "P0001";
        }

        int number = Integer.parseInt(lastProductId.split("P")[1]);
        number++;
        return String.format("P%04d", number);
    }
}
