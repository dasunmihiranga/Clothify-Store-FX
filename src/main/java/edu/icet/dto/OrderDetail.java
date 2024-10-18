package edu.icet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OrderDetail {
    private Order order;
    private String productId;
    private Integer qty;
    private Double productTotal;

    public OrderDetail(String productId, Integer qty, Double productTotal) {
        this.productId = productId;
        this.qty = qty;
        this.productTotal = productTotal;
    }

}
