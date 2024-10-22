package edu.icet.dto;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
@AllArgsConstructor
@Data
@ToString
@NoArgsConstructor
public class ViewOrderTblObj {
    private String id;
    private LocalDate date;
    private Double netTotal;
    private String customerId;
}
