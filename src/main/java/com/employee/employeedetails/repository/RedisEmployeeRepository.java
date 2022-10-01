package com.employee.employeedetails.repository;

import com.employee.employeedetails.entity.EmployeeDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Repository
public class RedisEmployeeRepository {
    private static final String KEY = "emp";

    private RedisTemplate<String, String> redisTemplate;
    private HashOperations<String, String, String> hashOperations;

    @Autowired
    public RedisEmployeeRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    public void save(EmployeeDetails employeeDetails , String dbName) {
        hashOperations.put(KEY, employeeDetails.getId()+":"+employeeDetails.getName(), dbName);
        redisTemplate.expire(KEY,60, TimeUnit.SECONDS);
    }

    public boolean check(String employeeName) {
        System.out.println("Name for key : "+employeeName);
        Map<String, String> map = hashOperations.entries(KEY);
        Set<String> set = map.keySet()
                .stream()
                .filter(s -> s.endsWith(employeeName))
                .collect(Collectors.toSet());
        System.out.println("check set key "+ set);
        Iterator<String> it = set.iterator();

        while (it.hasNext()) {
            if(it.next().endsWith(employeeName)) {return true;}
        }
        return false;
    }


    public String getEmployeeDatabase(int id){
        String dbName = (String) redisTemplate.opsForHash().get("employee_db",String.valueOf(id));
        return dbName;
    }

    public String getEmployeeDepartment(int id){
        String deptName = (String) redisTemplate.opsForHash().get("employee_department",String.valueOf(id));
        return deptName;
    }
}
