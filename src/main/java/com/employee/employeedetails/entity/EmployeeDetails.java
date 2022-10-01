package com.employee.employeedetails.entity;

import com.employee.employeedetails.dto.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetails implements Serializable {

    private int id;
    private String name;
    private String department;

    public EmployeeDetails(EmployeeDTO employeeDTO , String department){
        this.id = employeeDTO.getId();
        this.name = employeeDTO.getName();
        this.department = department;
    }
}
