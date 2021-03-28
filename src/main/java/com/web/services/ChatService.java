package com.web.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.entities.Chat;
import com.web.repositories.ChatRepo;

@Service
public class ChatService {
	@Autowired
	public ChatRepo chatRepo;
	@PersistenceContext
	protected EntityManager entityManager;

	public List<Chat> findChatByUserId(int userId) {
		String sql = "select * from tbl_chat where user_id_sender = '" + userId + "' or user_id_receiver = '" + userId
				+ "'";
		Query query = entityManager.createNativeQuery(sql, Chat.class);
		return query.getResultList();
	}

	public List<Chat> findChatByUserIdAndPartnerId(int userId, int partnerId) {
		String sql = "select * from tbl_chat where (user_id_sender = '" + userId + "' and user_id_receiver = '"
				+ partnerId + "') or (user_id_sender = '" + partnerId + "' and user_id_receiver = '" + userId + "')";
		Query query = entityManager.createNativeQuery(sql, Chat.class);
		return query.getResultList();
	}

	@Transactional(rollbackOn = Exception.class)
	public void deleteMessage(int id, int userId) {
		String sql = "delete from tbl_chat where id = '" + id + "' and (user_id_sender = '" + userId
				+ "' or user_id_receiver = '" + userId + "')";
		Query query = entityManager.createNativeQuery(sql);
		query.executeUpdate();
	}

	@Transactional(rollbackOn = Exception.class)
	public void deleteConversation(int userId, int partnerId) {
		String sql = "delete from tbl_chat where (user_id_sender = '" + userId + "' and user_id_receiver = '"
				+ partnerId + "') or (user_id_sender = '" + partnerId + "' and user_id_receiver = '" + userId + "')";
		Query query = entityManager.createNativeQuery(sql);
		query.executeUpdate();
	}
}
