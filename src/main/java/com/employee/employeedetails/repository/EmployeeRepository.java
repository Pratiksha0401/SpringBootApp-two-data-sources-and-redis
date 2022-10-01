package com.employee.employeedetails.repository;

import com.employee.employeedetails.dto.EmployeeDTO;
import com.employee.employeedetails.entity.EmployeeDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;

@Repository
public class EmployeeRepository {
    @Autowired
    @Qualifier("mysqlJdbc")
    JdbcTemplate jdbcTemplateMysql1;

    public EmployeeDetails findById(int id, Connection connection) {
        try {
            JdbcTemplate jdbcTemplateMysql1 = new JdbcTemplate(new SingleConnectionDataSource(connection, false));
            EmployeeDetails employee = jdbcTemplateMysql1.queryForObject("SELECT * FROM employee_details WHERE id=?",
                    BeanPropertyRowMapper.newInstance(EmployeeDetails.class), id);
            return employee;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public Integer findId(int id , Connection connection) {
        JdbcTemplate jdbcTemplateMysql1 = new JdbcTemplate(new SingleConnectionDataSource(connection, false));
        try {
            int empId = jdbcTemplateMysql1.queryForObject("SELECT id FROM employee_details WHERE id=?",
                    new Object[]{id}, int.class);
            return empId;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public int save(EmployeeDTO employeeDTO, String department , Connection connection) {
        System.out.println(connection);
        JdbcTemplate jdbcTemplateMysql1 = new JdbcTemplate(new SingleConnectionDataSource(connection, false));
        return jdbcTemplateMysql1.update("INSERT INTO employee_details ( id ,name ,department) VALUES(?,?,?)",
                employeeDTO.getId(),employeeDTO.getName(),department);
    }

    public int deleteById(int id, Connection connection) {
        JdbcTemplate jdbcTemplateMysql1 = new JdbcTemplate(new SingleConnectionDataSource(connection, false));
        return jdbcTemplateMysql1.update("DELETE FROM employee_details WHERE id=?", id);
    }

    public int update(int id ,EmployeeDetails employee, Connection connection) {
        JdbcTemplate jdbcTemplateMysql1 = new JdbcTemplate(new SingleConnectionDataSource(connection, false));
        return jdbcTemplateMysql1.update("UPDATE employee_details SET name=? , department=? WHERE id=?",
                new Object[] { employee.getName(), employee.getDepartment(), id });
    }

    public String findByName(EmployeeDetails employeeDetails) {
        try {
            String employeeName = jdbcTemplateMysql1.queryForObject("SELECT name FROM employee_details WHERE name=?",
                    new Object[]{employeeDetails.getName()}, String.class);
            System.out.println("Employee Name : "+employeeName);
            return employeeName;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public EmployeeDetails entryDetails(Connection connection, int id) {
        try {
            JdbcTemplate jdbcTemplateMysql1 = new JdbcTemplate(new SingleConnectionDataSource(connection, false));
            List<EmployeeDetails> employeeDetails = jdbcTemplateMysql1.query("select * from employee_details where id="+id+";",
                    new BeanPropertyRowMapper(EmployeeDetails.class));
            for (EmployeeDetails employeeDetail: employeeDetails) {
                return employeeDetail;
            }
            return null;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

}
