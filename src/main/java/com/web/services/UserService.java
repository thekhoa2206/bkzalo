package com.web.services;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.spec.SecretKeySpec;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

import com.web.common.SearchSomethings;
import com.web.entities.Friend;
import com.web.repositories.FriendRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.entities.Post;
import com.web.entities.Roles;
import com.web.entities.User;
import com.web.repositories.UserRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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
	@Autowired
	public FriendRepo friendRepo;

	public static final String SECRET_KEY = "oeRaYY7Wo24sDqKSX3IM9ASGmdGPmkTd9jo1QTy4b7P9Ze5_9hKolVX8xNrQDcNRfVEdTZNOuOyqEGhXEbdJI-ZQ19k_o9MI0y3eZN2lp9jow55FfXMiINEdt1XR85VipRLSOkT6kSpzs2x-jbLDiz9iFVzkd81YKxMgPA7VfZeQUm4n-mOmnWMaVX30zGFU4L3oPBctYKkl4dYfqYWqRNfrgPJVi5DGFjywgxx0ASEiJHtV72paI3fDR2XwlSkyhhmY-ICjCRmsJN4fX1pdoL8a18-aQrvyu4j0Os6dVPYIoPvvY0SAZtWYKHfM15g7A3HD4cVREf9cUsprCRK93w";
	public static final Long ttlMillis = 86400000L;

	public User findUserById(final int id) {

		String sql = "select * from tbl_users where id = '" + id + "'";
		Query query = entityManager.createNativeQuery(sql, User.class);
		return (User) query.getSingleResult();
	}
	
	public List<Roles> findRoleById(final int id) {

		String sql = "select * from tbl_roles where id = '" + id + "'";
		Query query = entityManager.createNativeQuery(sql, Roles.class);
		return query.getResultList();
	}

//	public User findUserById(final int id1,final int id2) {
//
//		String sql = "select * from tbl_users where id = '" + id1 + "' or id ='"+id2+"' ";
//		Query query = entityManager.createNativeQuery(sql, User.class);
//		return (User) query.getSingleResult();
//	}

	public User findUserByPhone(final String phone) {
		String sql = "select * from tbl_users where phone = '" + phone + "'";
		Query query = entityManager.createNativeQuery(sql, User.class);
		return (User) query.getSingleResult();
	}


	public List<Post> findPostById(final int id) {
		String sql = "select * from tbl_posts where id = '" + id + "'";
		Query query = entityManager.createNativeQuery(sql, Post.class);
		return query.getResultList();
	}

	public List<Friend> findFriendRequestById(final SearchSomethings searchSomethings) {
//		String sql = "select * from tbl_friends AS t1, tbl_users AS t2 where t1.id_user_b = '" + id + "' AND t2.id = '" + t1.id_user_a + "' ";
//		String sql = "select * from tbl_friends , tbl_users where tbl_friends.id_user_b = '" + id + "' AND tbl_users.id = '" + tbl_friends.id_user_a + "' ";
		String sql = "select * from tbl_friends where id_user_b = '" + searchSomethings.getKeyword()
				+ "' AND is_accept = '" + 0 + "' ";

		Query query = entityManager.createNativeQuery(sql, Friend.class);
		return query.getResultList();
	}

	public List<Friend> findFriendRequestByIdB(final int id) {

		String sql = "select * from tbl_friends where id_user_b = '" + id
				+ "' AND is_accept = '" + 0 + "' ";

		Query query = entityManager.createNativeQuery(sql, Friend.class);
		return query.getResultList();
	}

	public List<Friend> findFriendRequestByIdA(final int id) {

		String sql = "select * from tbl_friends where id_user_a = '" + id
				+ "' AND is_accept = '" + 0 + "' ";

		Query query = entityManager.createNativeQuery(sql, Friend.class);
		return query.getResultList();
	}

	public Friend findFriendRequestById(final Integer idA, final Integer idB) {

		String sql = "select * from tbl_friends where id_user_b = '" + idB
				+ "' AND is_accept = '" + 0 + "' AND id_user_a = '" + idA + "' ";

		Query query = entityManager.createNativeQuery(sql, Friend.class);
		return (Friend) query.getSingleResult();
	}

	//Gửi yêu cầu và chấp nhận lời mời kết bạn
	@Transactional(rollbackOn = Exception.class)
	public void saveFriendRequest(Friend friendData) throws Exception {
		try {
			friendRepo.save(friendData);
		} catch (Exception e) {
			throw e;
		}
	}

	//Xóa yêu cầu kết bạn
	@Transactional(rollbackOn = Exception.class)
	public void deleteFriendRequest(final Integer idA, final Integer idB) {
		String sql = "delete from tbl_friends where id_user_b = '" + idB
				+ "' AND is_accept = '" + 0 + "' AND id_user_a = '" + idA + "' ";
		Query query = entityManager.createNativeQuery(sql);
		query.executeUpdate();
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

	public List<User> findAllUser() {
		String sql = "select * from tbl_users";
		Query query = entityManager.createNativeQuery(sql, Post.class);
		return query.getResultList();
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

	// lấy phone từ token
	public String getPhoneNumberFromToken(String jwt) {
		try {
			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
					.parseClaimsJws(jwt).getBody();

			return claims.getIssuer();

		} catch (ExpiredJwtException | IllegalArgumentException e) {
			return null;
		}
	}

	public User isUserInRole(String id){
//		and roles = '" + 1 + "'
		String sql = "select * from tbl_users where id = '" + id + "' ";
		Query query = entityManager.createNativeQuery(sql, User.class);
		return (User) query.getSingleResult();
	}

	public String createJWT(String issuer) {
		String UUID = findUserByPhone(issuer).getName();
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
	
	// kiểm tra kí tự đặc biệt
	public boolean getSpecialCharacterCount(String s) {
		Pattern p = Pattern.compile("[^A-Za-z0-9]");
		Matcher m = p.matcher(s);

		boolean b = m.find();
		if (b == true)
			return false;
		else
			return true;
	}  
}

