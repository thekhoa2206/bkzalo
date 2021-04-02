package com.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.entities.Roles;

@Repository
public interface RoleRepo extends JpaRepository<Roles, Integer> {

}
