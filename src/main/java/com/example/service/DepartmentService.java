package com.example.service;

import com.example.model.Department;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class DepartmentService {

    @Cacheable(value = "departments", key = "'departments' + #departmentId")
    public Department getDepartmentById(Long departmentId) {
        log.info("Fetching department based on departmentId from cache");
        slowMethod();
        return getAllDepartments().stream()
                .filter(department -> department.getDepartId().equals(departmentId))
                .findFirst()
                .orElse(null);
    }

    @Cacheable(value = "departments", key = "'allDepartments'")
    public List<Department> getDepartments() {
        log.info("Fetching all departments from cache");
        slowMethod(); // Simulating a slow method to demonstrate caching
        return getAllDepartments();
    }

    @CacheEvict(value = "departments", allEntries = true)
    public void evictAllCacheValues() {
    }

    @CacheEvict(value = "departments", key = "'departments' + #cacheKey")
    public void evictSingleCacheValue(String cacheKey) {
    }

    private List<Department> getAllDepartments() {
        log.info("Fetching all departments");
        return Arrays.asList(
                new Department(1L, "HR", "Human Resources", "HR001"),
                new Department(2L, "IT", "Information Technology", "IT001"),
                new Department(3L, "Finance", "Financial Department", "FIN001")
        );
    }

    private void slowMethod() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            log.error("Error in slowMethod", e);
        }
    }

}
