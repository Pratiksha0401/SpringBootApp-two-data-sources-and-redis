package com.employee.employeedetails.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    String message;
    Object data;
    String statusCode;

    public ResponseDTO(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}