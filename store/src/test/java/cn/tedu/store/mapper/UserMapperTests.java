package cn.tedu.store.mapper;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTests {

	@Autowired
	private UserMapper mapper;

	@Test
	public void save() {
		User user = new User();
		user.setUsername("admin");
		user.setPassword("1234");
		Integer rows = mapper.save(user);
		System.err.println("rows:" + rows);
	}

	@Test
	public void updatePassword() {
		Integer uid = 6;
		String password = "1234";
		String modifiedUser = "超级管理员";
		Date modifiedTime = new Date();
		Integer rows = mapper.updatePassword(uid, password, modifiedUser, modifiedTime);
		System.err.println(rows);
	}

	@Test
	public void updateInfo() {
		User user = new User();
		user.setUid(8);
		user.setGender(0);
		user.setPhone("12354654687");
		user.setEmail("root@qq.com");
		Integer rows = mapper.updateInfo(user);
		System.err.println(rows);
	}

	@Test
	public void updateAvatar() {
		Integer uid = 8;
		String avatar = "持久层测试头像";
		String modifideUser = "超级管理员";
		Date modifideTime = new Date();
		Integer rows = mapper.updateAvatar(uid, avatar, modifideUser, modifideTime);
		System.err.println(rows);
	}

	@Test
	public void findByUid() {
		Integer uid = 6;
		User result = mapper.findByUid(uid);
		System.err.println(result);
	}

	@Test
	public void findByUsername() {

		String username = "admin";
		User result = mapper.findByUsername(username);
		System.err.println(result);
	}

}
