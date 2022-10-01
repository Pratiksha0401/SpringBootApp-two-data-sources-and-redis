package com.employee.employeedetails.service;

import com.employee.employeedetails.dto.EmployeeDTO;
import com.employee.employeedetails.entity.EmployeeDetails;
import com.employee.employeedetails.exception.EmployeeDetailsException;
import com.employee.employeedetails.repository.EmployeeRepository;
import com.employee.employeedetails.repository.RedisEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.HashMap;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RedisEmployeeRepository redisEmployeeRepository;

    @Value("${redis.host:localhost}")
    public String redisHost ;

    @Autowired
    private HashMap<String, Connection> myConnection;

    public EmployeeDetails getEmployeeById(int id) {
        String dbName = redisEmployeeRepository.getEmployeeDatabase(id);
        if(dbName==null){
            throw new EmployeeDetailsException("No db name found for ID", "404");
        }
        Integer empId = employeeRepository.findId(id, myConnection.get(dbName));
        if(empId==null){
            throw new EmployeeDetailsException("Id not found","404");
        }
        return employeeRepository.findById(id, myConnection.get(dbName));
    }

    public EmployeeDetails addEmployee(EmployeeDTO employeeDTO){
        String dbName = redisEmployeeRepository.getEmployeeDatabase(employeeDTO.getId());
        if(dbName==null){
            throw new EmployeeDetailsException("No db name found for ID", "404");
        }
        String department = redisEmployeeRepository.getEmployeeDepartment(employeeDTO.getId());
        if(department==null){
            department="default";
        }
        int isIdExist = employeeRepository.findId(employeeDTO.getId() , myConnection.get(dbName));
        if(isIdExist == employeeDTO.getId()){
            throw new EmployeeDetailsException("Id Already exist", "500");
        }
        int result = employeeRepository.save(employeeDTO ,department, myConnection.get(dbName));
        return employeeRepository.entryDetails( myConnection.get(dbName), employeeDTO.getId());
    }

    public int deleteEmployee(Integer id){
        String dbName = redisEmployeeRepository.getEmployeeDatabase(id);
        if(dbName==null){
            throw new EmployeeDetailsException("No db name found for ID", "404");
        }
        Integer empId = employeeRepository.findId(id, myConnection.get(dbName));
        if(empId==null){
            throw new EmployeeDetailsException("Id not found","404");
        }
        return employeeRepository.deleteById(id, myConnection.get(dbName));
    }

    public int updateEmployee(Integer id,EmployeeDetails employeeDetails){
        String dbName = redisEmployeeRepository.getEmployeeDatabase(id);
        if(dbName==null){
            throw new EmployeeDetailsException("No db name found for ID", "404");
        }
        Integer empId = employeeRepository.findId(id,myConnection.get(dbName));
        if(empId==null){
            throw new EmployeeDetailsException("Id not found","404");
        }
        System.out.println("emp Id update =>"+ empId);
        return employeeRepository.update(empId,employeeDetails, myConnection.get(dbName));
    }

}
