package com.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.entities.Chat;

@Repository
public interface ChatRepo extends JpaRepository<Chat, Integer> {

}
