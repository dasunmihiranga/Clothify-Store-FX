package edu.icet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
public class ProductEntity {
    @Id
    private String productId;
    private String name;
    private String size;
    private Integer qty;
    private Double unitPrice;
    private String category;

    @ManyToOne
    @JoinColumn(name="supplierId")
    private SupplierEntity supplier;

}
