package com.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.entities.Report;

@Repository
public interface ReportRepo extends JpaRepository<Report, Integer>{

}
