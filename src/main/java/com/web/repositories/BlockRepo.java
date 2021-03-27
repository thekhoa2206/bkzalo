package com.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.entities.Block;

@Repository
public interface BlockRepo extends JpaRepository<Block, Integer> {

}
