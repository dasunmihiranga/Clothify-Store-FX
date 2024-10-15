package edu.icet.service.custom;

import edu.icet.dto.Product;
import edu.icet.dto.Supplier;
import edu.icet.repository.custom.ProductDao;
import edu.icet.service.SuperService;
import javafx.collections.ObservableList;

public interface ProductService  extends SuperService {
    boolean addProduct(Product product);

    ObservableList<Product> getAllProduct();

    boolean updateProduct(Product product);

    boolean deleteProduct(String id);

    Product searchByName (String name);
}
