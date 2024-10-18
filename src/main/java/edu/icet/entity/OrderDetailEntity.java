package edu.icet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data

@Entity

public class OrderDetailEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "OrderId")
    private OrderEntity order;

    @Id
    private String productId;
    private Integer qty;
    private Double productTotal;

    public OrderDetailEntity(String productId, Integer qty, Double productTotal) {
        this.productId = productId;
        this.qty = qty;
        this.productTotal = productTotal;
    }
}
