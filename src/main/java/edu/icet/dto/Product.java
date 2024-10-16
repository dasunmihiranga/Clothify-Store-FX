package edu.icet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Product {
    private String productId;
    private String name;
    private String size;
    private Integer qty;
    private Double unitPrice;
    private String category;
    private Supplier supplier;


}
