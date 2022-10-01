package com.employee.employeedetails.exception;

import com.employee.employeedetails.dto.ResponseDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class EmployeeExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception){
        List<ObjectError> errorList = exception.getBindingResult().getAllErrors();
        List<String> errMsg = errorList.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        ResponseDTO responseDTO = new ResponseDTO("Exceptions while processing REST Request",errMsg,"300");
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmployeeDetailsException.class)
    public ResponseEntity<ResponseDTO> employeeDetailsExceptionHandler(EmployeeDetailsException employeeDetailsException){
        ResponseDTO responseDTO = new ResponseDTO("Exception while processing request", employeeDetailsException.getMessage(),employeeDetailsException.getStatusCode());
        return  new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
}
