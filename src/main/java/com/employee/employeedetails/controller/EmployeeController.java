package com.employee.employeedetails.controller;

import com.employee.employeedetails.dto.EmployeeDTO;
import com.employee.employeedetails.dto.ResponseDTO;
import com.employee.employeedetails.entity.EmployeeDetails;
import com.employee.employeedetails.repository.RedisEmployeeRepository;
import com.employee.employeedetails.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    RedisEmployeeRepository redisEmployeeRepository;

    @GetMapping( "/{id}")
    public ResponseEntity<ResponseDTO> getEmployeeDetailById(@PathVariable int id){
        EmployeeDetails employeeDetails = employeeService.getEmployeeById(id);
        ResponseDTO responseDTO = new ResponseDTO("Employee Details by Id",employeeDetails,"200");
        return new ResponseEntity<>(responseDTO , HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addEmployeeDetails(@RequestBody @Valid EmployeeDTO employeeDTO){
       EmployeeDetails employeeDetails1 = employeeService.addEmployee(employeeDTO);
        redisEmployeeRepository.save(employeeDetails1,"mysql");
        ResponseDTO responseDTO = new ResponseDTO("Employee Details Successfully Added",employeeDetails1,"201");
        return new ResponseEntity<>(responseDTO , HttpStatus.CREATED);
    }

    @DeleteMapping( "/{id}")
    public ResponseEntity<ResponseDTO> deleteEmployeeDetailById(@PathVariable int id){
        int response = employeeService.deleteEmployee(id);
        ResponseDTO responseDTO = new ResponseDTO("Employee Details Deleted Successfully for id :"+id,response,"200");
        return new ResponseEntity<>(responseDTO , HttpStatus.OK);
    }

    @PutMapping( "/{id}")
    public ResponseEntity<ResponseDTO> updateEmployeeDetailById(@PathVariable int id,@RequestBody @Valid EmployeeDetails employeeDetails){
        int response = employeeService.updateEmployee(id,employeeDetails);
        ResponseDTO responseDTO = new ResponseDTO("Employee Details Updated Successfully for id :"+id,response,"200");
        return new ResponseEntity<>(responseDTO , HttpStatus.OK);
    }
}
