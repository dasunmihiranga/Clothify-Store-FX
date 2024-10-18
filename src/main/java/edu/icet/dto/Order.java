package edu.icet.dto;

import edu.icet.entity.CustomerEntity;
import edu.icet.entity.OrderDetailEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Order {
   private String id;
   private Customer customer;
   private LocalDate date;
   private Double netTotal;

//   public Order(String id, Customer customer, LocalDate date,  Double netTotal) {
//      this.id=id;
//      this.customer=customer;
//      this.date=date;
//      this.netTotal=netTotal;
//
//   }


}
