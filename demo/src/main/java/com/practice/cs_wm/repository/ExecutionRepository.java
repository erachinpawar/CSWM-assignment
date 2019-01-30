package com.practice.cs_wm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.cs_wm.model.Execution;

public interface ExecutionRepository extends JpaRepository<Execution, Long> {

}
