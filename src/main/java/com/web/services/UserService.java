package com.web.services;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.spec.SecretKeySpec;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

import com.web.Response.*;
import com.web.common.SearchSomethings;
import com.web.entities.*;
import com.web.repositories.FriendRepo;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.repositories.UserRepo;

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

	/*public List<Friend> findFriendRequestByIdB(final int id) {

		String sql = "select id_user_a from tbl_friends where id_user_b = '" + id
				+ "' AND is_accept = '" + 0 + "' ";

		Query query = entityManager.createNativeQuery(sql, Friend.class);
		return query.getResultList();
	}*/

	public GetListFriendRequestResponse findFriendRequestByIdB(final int id) {

		String sql = "select * from tbl_friends where id_user_b = '" + id
				+ "' AND is_accept = '" + 0 + "' ";
		Query query = entityManager.createNativeQuery(sql, Friend.class);

		List<Friend> friends = query.getResultList();
		List<User> results = new ArrayList<>();
		List<String> created = new ArrayList<>();
		GetListFriendRequestResponse getListFriendRequestResponse = new GetListFriendRequestResponse();
		for(Friend friend : friends){
				results.add(friend.getUserAId());
				created.add(friend.getCreated());
		}

		getListFriendRequestResponse = getListUserRequestResponse(results,created);

		return getListFriendRequestResponse;


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

	public Friend findFriendRequestByIdCheck(final Integer idA, final Integer idB) {

		String sql = "select * from tbl_friends where id_user_b = '" + idB
				+ "' AND id_user_a = '" + idA + "' ";

		Query query = entityManager.createNativeQuery(sql, Friend.class);
		try {
			return (Friend) query.getSingleResult();
		}catch (NoResultException e){
			return null;
		}
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
	public GetListFriendResponse findFriendInfo(final int id) {
		String sql = "select * from tbl_friends where id_user_b = '" + id + "' or id_user_a = '" + id + "' and is_accept = true";
		Query query = entityManager.createNativeQuery(sql, Friend.class);
		List<Friend> friends = query.getResultList();
		List<UserResponse> userResponses = new ArrayList<>();
		List<User> results = new ArrayList<>();
		List<String> created = new ArrayList<>();
		GetListFriendResponse getListFriendResponse = new GetListFriendResponse();
		for(Friend friend : friends){
			if(friend.getUserAId().getId() == id && friend.getCreated() != null){
				results.add(friend.getUserBId());
				created.add(friend.getCreated());
			}else if(friend.getUserBId().getId() == id && friend.getCreated() != null){
				results.add(friend.getUserAId());
				created.add(friend.getCreated());
			}
		}

		getListFriendResponse = getListUserResponse(results,created);

		return getListFriendResponse;
	}

	//Convert User -> UserResponse
	public GetListFriendResponse getListUserResponse(List<User> list, List<String> created){
		GetListFriendResponse getListFriendResponse = new GetListFriendResponse();
		ListFriendsResponse listFriendsResponse = new ListFriendsResponse();
		List<UserResponse> userResponses = new ArrayList<>();
		UserResponse userResponse = new UserResponse();
		int i = 0;
		for(User user : list){
			userResponse = new UserResponse(user.getId(),user.getName(),user.getAvatar(),created.get(i));
			i++;
			userResponses.add(userResponse);
		}
		listFriendsResponse.setFriends(userResponses);
		//listFriendsResponse.setTotal(userResponses.size());
		getListFriendResponse.setData(listFriendsResponse);
		return getListFriendResponse;
	}

	//Convert User -> UserResponse (Request)
	public GetListFriendRequestResponse getListUserRequestResponse(List<User> list, List<String> created){
		GetListFriendRequestResponse getListFriendRequestResponse = new GetListFriendRequestResponse();
		ListFriendRequestResponse listFriendRequestResponse = new ListFriendRequestResponse();
		List<UserResponse> userResponses = new ArrayList<>();
		UserResponse userResponse = new UserResponse();
		int i = 0;
		for(User user : list){
			userResponse = new UserResponse(user.getId(),user.getName(),user.getAvatar(),created.get(i));
			i++;
			userResponses.add(userResponse);
		}
		listFriendRequestResponse.setFriends(userResponses);
		//listFriendsResponse.setTotal(userResponses.size());
		getListFriendRequestResponse.setData(listFriendRequestResponse);
		return getListFriendRequestResponse;
	}

	public List<UserResponse> count(List<UserResponse> list, int index, int count) {

		int lastId = 0;
		List<UserResponse> data = new ArrayList<UserResponse>();
//		List<Post> data = postRepo.findAll();

			int last = index + count;
			int a = 0;
			if (list.size() < last) {
				for (int i = index; i < list.size(); i++) {
					data.add(list.get(i));
				}
			} else {
				for (int i = index; i < last; i++) {
					data.add(list.get(i));
				}
			}
			if(data.size() > 0){
				lastId = data.get(data.size() - 1).getId();
			}

			return data;

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

		} catch (ExpiredJwtException | IllegalArgumentException | SignatureException e) {
			return null;
		}
	}

	public User isUserInRole(String id){
//		and roles = '" + 1 + "'
		String sql = "select * from tbl_users where id = '" + id + "' ";
		Query query = entityManager.createNativeQuery(sql, User.class);
		return (User) query.getSingleResult();
	}



//	@Override
//	public String getMD5(String input) {
//		try {
//
//			// Static getInstance method is called with hashing MD5
//			MessageDigest md = MessageDigest.getInstance("MD5");
//
//			// digest() method is called to calculate message digest
//			// of an input digest() return array of byte
//			byte[] messageDigest = md.digest(input.getBytes());
//
//			// Convert byte array into signum representation
//			BigInteger no = new BigInteger(1, messageDigest);
//
//			// Convert message digest into hex value
//			String hashtext = no.toString(16);
//			while (hashtext.length() < 32) {
//				hashtext = "0" + hashtext;
//			}
//			return hashtext;
//		}
//
//		// For specifying wrong message digest algorithms
//		catch (NoSuchAlgorithmException e) {
//			throw new RuntimeException(e);
//		}
//	}


	public String createJWT(String issuer) {
		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		// Let's set the JWT Claims

		JwtBuilder builder = Jwts.builder().setIssuedAt(now).setIssuer(issuer).setAudience("")
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

