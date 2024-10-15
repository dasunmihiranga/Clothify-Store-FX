package edu.icet.service.custom;

import edu.icet.dto.Employee;
import edu.icet.dto.Supplier;
import edu.icet.service.SuperService;
import javafx.collections.ObservableList;

public interface SupplierService extends SuperService {

    boolean addSupplier(Supplier supplier);

    ObservableList<Supplier> getAllSupplier();

    boolean updateSupplier(Supplier supplier);

    boolean deleteSupplier(String id);

    Supplier searchByName (String name);
}
