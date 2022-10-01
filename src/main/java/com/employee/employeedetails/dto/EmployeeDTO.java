package com.employee.employeedetails.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private int id;

    @Pattern(regexp = "^[A-Z]{1}[a-zA-z\\s]{2,}$", message = "Employee Name not Valid")
    @NotBlank(message = "Name must not blank")
    private String name;
}
