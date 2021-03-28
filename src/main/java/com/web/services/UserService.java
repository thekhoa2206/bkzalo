package com.web.services;

import java.security.Key;
import java.util.Date;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

import com.web.common.SearchSomethings;
import com.web.entities.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.entities.Post;
import com.web.entities.User;
import com.web.repositories.UserRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserService {
	@PersistenceContext
	protected EntityManager entityManager;
	@Autowired
	public UserRepo userRepo;

	public static final String SECRET_KEY = "oeRaYY7Wo24sDqKSX3IM9ASGmdGPmkTd9jo1QTy4b7P9Ze5_9hKolVX8xNrQDcNRfVEdTZNOuOyqEGhXEbdJI-ZQ19k_o9MI0y3eZN2lp9jow55FfXMiINEdt1XR85VipRLSOkT6kSpzs2x-jbLDiz9iFVzkd81YKxMgPA7VfZeQUm4n-mOmnWMaVX30zGFU4L3oPBctYKkl4dYfqYWqRNfrgPJVi5DGFjywgxx0ASEiJHtV72paI3fDR2XwlSkyhhmY-ICjCRmsJN4fX1pdoL8a18-aQrvyu4j0Os6dVPYIoPvvY0SAZtWYKHfM15g7A3HD4cVREf9cUsprCRK93w";
	public static final Long ttlMillis = 86400000L;

	public User findUserById(final int id) {

		String sql = "select * from tbl_users where id = '" + id + "'";
		Query query = entityManager.createNativeQuery(sql, User.class);
		return (User) query.getSingleResult();
	}

	public User finUserByPhone(final String phone) {
		String sql = "select * from tbl_users where phone = '" + phone + "'";
		Query query = entityManager.createNativeQuery(sql, User.class);
		return (User) query.getSingleResult();
	}

	public List<Post> findPostById(final int id) {
		String sql = "select * from tbl_posts where id = '" + id + "'";
		Query query = entityManager.createNativeQuery(sql, Post.class);
		return query.getResultList();
	}

	public List<Friend> findFriendById(final SearchSomethings searchSomethings) {
//		String sql = "select * from tbl_friends AS t1, tbl_users AS t2 where t1.id_user_b = '" + id + "' AND t2.id = '" + t1.id_user_a + "' ";
//		String sql = "select * from tbl_friends , tbl_users where tbl_friends.id_user_b = '" + id + "' AND tbl_users.id = '" + tbl_friends.id_user_a + "' ";
		String sql = "select * from tbl_friends where id_user_b = '" + searchSomethings.getKeyword()
				+ "' AND is_accept = '" + 0 + "' ";

		Query query = entityManager.createNativeQuery(sql, Friend.class);
		return query.getResultList();
	}

	@Transactional(rollbackOn = Exception.class)
	public void saveUser(User user) throws Exception {
		try {
			if (user.getId() != null) { // chỉnh sửa
				// lấy dữ liệu cũ của sản phẩm
				User userInDb = userRepo.findById(user.getId()).get();

			}

			userRepo.save(user);
		} catch (Exception e) {
			throw e;
		}
	}

	// SearchByID and isRequest =1 => Tìm bạn của user
	public List<Friend> findFriendInfo(final int id) {
		String sql = "select * from tbl_friends where id_user_b = '" + id + "' and is_accept = true";
		Query query = entityManager.createNativeQuery(sql, Friend.class);
		return query.getResultList();
	}

	@Transactional(rollbackOn = Exception.class)
	public void saveGuestUser(User user) throws Exception {
		try {
			userRepo.save(user);
		} catch (Exception e) {
			throw e;
		}
	}

	public String createJWT(String issuer) {
		String UUID = finUserByPhone(issuer).getName();
		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		// Let's set the JWT Claims

		JwtBuilder builder = Jwts.builder().setIssuedAt(now).setIssuer(issuer).setAudience(UUID)
				.signWith(signatureAlgorithm, signingKey);

		// if it has been specified, let's add the expiration
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}

		// Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
	}

	public boolean validateToken(String jwt) {
		// This line will throw an exception if it is not a signed JWS (as expected)

		try {
			@SuppressWarnings({ "deprecation", "unused" })
			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
					.parseClaimsJws(jwt).getBody();
			if (claims != null) {
				return true;
			}
		} catch (io.jsonwebtoken.SignatureException | io.jsonwebtoken.ExpiredJwtException | MalformedJwtException e) {
			return false;
		}
		return false;
	}
}
