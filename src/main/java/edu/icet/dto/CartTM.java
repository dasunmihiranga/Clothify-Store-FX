package edu.icet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartTM {
    private String productId;
    private String description;
    private Integer qty;
    private Double unitPrice;
    private Double total;


}
