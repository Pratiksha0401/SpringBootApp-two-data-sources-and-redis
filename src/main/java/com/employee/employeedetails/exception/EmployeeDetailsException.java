package com.employee.employeedetails.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetailsException extends RuntimeException{
    private String message;
    private String statusCode;
}