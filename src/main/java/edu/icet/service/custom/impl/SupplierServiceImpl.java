package edu.icet.service.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.dto.Customer;
import edu.icet.dto.Supplier;
import edu.icet.entity.CustomerEntity;
import edu.icet.entity.SupplierEntity;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.SupplierDao;
import edu.icet.service.custom.SupplierService;
import edu.icet.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SupplierServiceImpl implements SupplierService {
    SupplierDao supplierDao = DaoFactory.getInstance().getDaoType(DaoType.SUPPLIER);

    @Override
    public boolean addSupplier(Supplier supplier) {
        SupplierEntity entity = new ObjectMapper().convertValue(supplier, SupplierEntity.class);
        return supplierDao.save(entity);
    }

    @Override
    public ObservableList<Supplier> getAllSupplier() {
        ObservableList<SupplierEntity> supplierEntities = supplierDao.searchAll();

        ObservableList<Supplier> supplierList = FXCollections.observableArrayList();

        supplierEntities.forEach(supplierEntity -> {
            System.out.println("<<<<<<<<<<<<<<<<<<<< "+supplierEntity);
            supplierList.add(new ObjectMapper().convertValue(supplierEntity, Supplier.class) );
        });


        return supplierList;

    }

    @Override
    public boolean updateSupplier(Supplier supplier) {
        return supplierDao.update(new ObjectMapper().convertValue(supplier, SupplierEntity.class));
    }

    @Override
    public boolean deleteSupplier(String id) {
        return supplierDao.delete(id);
    }

    @Override
    public Supplier searchByName(String name) {
        SupplierEntity supplierEntity = supplierDao.search(name);
        return new ObjectMapper().convertValue(supplierEntity, Supplier.class);
    }

    @Override
    public Supplier searchById(String id) {
        SupplierEntity supplierEntity =supplierDao.searchById(id);
        return new ObjectMapper().convertValue(supplierEntity,Supplier.class);
    }

    @Override
    public String generateSupplierId() {
        String lastSupplierId = supplierDao.getLatestId();
        if (lastSupplierId==null){
            return "S0001";
        }

        int number = Integer.parseInt(lastSupplierId.split("S")[1]);
        number++;
        return String.format("S%04d", number);
    }
}
