package edu.icet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor

@Entity
public class EmployeeEntity {
    @Id
    private String id;
    private String name;
    private String email;
    private String address;
    private String password;
}
