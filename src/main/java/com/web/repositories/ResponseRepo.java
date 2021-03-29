package com.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.entities.AjaxResponse;

@Repository
public interface ResponseRepo extends JpaRepository<AjaxResponse, Integer>{

}
