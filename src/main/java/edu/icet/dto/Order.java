package edu.icet.dto;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private String orderId;
    private LocalDate orderData;
    private String customerId;
    private List<OrderDetail> orderDetails;
    private Double amount;
}
