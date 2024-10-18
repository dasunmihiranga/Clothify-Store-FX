package edu.icet.service.custom;

import edu.icet.dto.Customer;
import edu.icet.dto.Employee;
import edu.icet.dto.Order;
import edu.icet.dto.OrderDetail;
import edu.icet.entity.OrderDetailEntity;
import edu.icet.service.SuperService;
import javafx.collections.ObservableList;

public interface OrderService extends SuperService {
    boolean addOrder(Order order , ObservableList<OrderDetailEntity> orderDetails);

    ObservableList<Order> getAllOrders();

    Order searchById (String id);

    String generateOrderId();
}
