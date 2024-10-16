package edu.icet.repository.custom;

import edu.icet.entity.CustomerEntity;
import edu.icet.entity.SupplierEntity;
import edu.icet.repository.CrudDao;

public interface SupplierDao extends CrudDao<SupplierEntity,String> {
    SupplierEntity searchById (String id);
}
