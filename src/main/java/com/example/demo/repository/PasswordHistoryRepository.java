package com.example.demo.repository;

import com.example.demo.entity.PasswordHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Integer> {

    @Query("SELECT ph.passwordHash FROM PasswordHistory ph WHERE ph.employee.employeeId = :employeeId")
    Page<String> findRecentPasswords(@Param("employeeId") Integer employeeId, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO PasswordHistory (employee_id, password_hash) VALUES (:employeeId, :passwordHash)", nativeQuery = true)
    void savePasswordHistory(Integer employeeId, String passwordHash);

}