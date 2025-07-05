package com.example.controller;

import com.example.model.Department;
import com.example.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping(value = "/department", produces = "application/json")
    public ResponseEntity<Department> getDepartmentById(@RequestParam Long departmentId) {
        log.info("Fetching department by ID: {}", departmentId);
        Department department = departmentService.getDepartmentById(departmentId);
        if (Objects.nonNull(department)) {
            return ResponseEntity.ok(department);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/departments", produces = "application/json")
    public ResponseEntity<List<Department>> getAllDepartments() {
        log.info("Fetching all departments");
        List<Department> departments = departmentService.getDepartments();
        if (CollectionUtils.isNotEmpty(departments)) {
            return ResponseEntity.ok(departments);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/evictAllCacheValues", produces = "application/json")
    public void evictAllCacheValues() {
        log.info("Evicting all cache values");
        departmentService.evictAllCacheValues();
    }

    @GetMapping(value = "/evictSingleCacheValue", produces = "application/json")
    public void evictSingleCacheValue(@RequestParam String cacheKey) {
        log.info("Evicting single cache value for key: {}", cacheKey);
        departmentService.evictSingleCacheValue(cacheKey);
    }

}
