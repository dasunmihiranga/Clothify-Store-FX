package edu.icet.repository.custom;

import edu.icet.entity.OrderDetailEntity;
import edu.icet.entity.OrderEntity;
import edu.icet.repository.CrudDao;
import javafx.collections.ObservableList;

public interface OrderDao extends CrudDao<OrderEntity,String> {

}
