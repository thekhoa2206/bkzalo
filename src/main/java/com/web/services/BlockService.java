package com.web.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

@Service
public class BlockService {
	@PersistenceContext
	protected EntityManager entityManager;
}
