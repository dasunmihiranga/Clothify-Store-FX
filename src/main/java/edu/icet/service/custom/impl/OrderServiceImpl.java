package edu.icet.service.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.dto.Customer;
import edu.icet.dto.Employee;
import edu.icet.dto.Order;
import edu.icet.dto.OrderDetail;
import edu.icet.entity.*;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.EmployeeDao;
import edu.icet.repository.custom.OrderDao;
import edu.icet.service.custom.OrderService;
import edu.icet.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    OrderDao orderDao = DaoFactory.getInstance().getDaoType(DaoType.ORDER);

    @Override
    public boolean addOrder(Order order,ObservableList<OrderDetailEntity> orderDetails) {

        System.out.println(order);
        CustomerEntity customer=new ObjectMapper().convertValue(order.getCustomer(),CustomerEntity.class);
        OrderEntity orderEntity=new OrderEntity(order.getId(),customer,null,order.getDate(),order.getNetTotal());
        for(OrderDetailEntity orderDetailEntity:orderDetails){
            orderDetailEntity.setOrder(orderEntity);
            System.out.println(orderDetailEntity);
        }
        orderEntity.setOrderDetails(orderDetails);
        System.out.println("orderEntity");


        return orderDao.save(orderEntity);

    }

    @Override
    public ObservableList<Order> getAllOrders() {
        ObservableList<OrderEntity> orderEntities = orderDao.searchAll();

        ObservableList<Order> orderList = FXCollections.observableArrayList();

        orderEntities.forEach(orderEntity -> {
            System.out.println("<<<<<<<<<<<<<<<<<<<< "+orderEntity);
            orderList.add(new ObjectMapper().convertValue(orderEntity, Order.class) );
        });
        return orderList;
    }

    @Override
    public Order searchById(String id) {
        OrderEntity orderEntity =orderDao.searchById(id);
        return new ObjectMapper().convertValue(orderEntity, Order.class);
    }

    @Override
    public String generateOrderId() {
        String lastOrderId = orderDao.getLatestId();
        if (lastOrderId==null){
            return "OR0001";
        }

        int number = Integer.parseInt(lastOrderId.split("OR")[1]);
        number++;
        return String.format("OR%04d", number);
    }
}
