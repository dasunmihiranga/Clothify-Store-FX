package edu.icet.service.custom;

import edu.icet.dto.*;
import edu.icet.entity.OrderDetailEntity;
import edu.icet.service.SuperService;
import javafx.collections.ObservableList;

public interface OrderService extends SuperService {
    boolean addOrder(Order order , ObservableList<OrderDetailEntity> orderDetails);

    ObservableList<ViewOrderTblObj> getAllOrders();

    Order searchById (String id);

    String generateOrderId();
}
