package cn.tedu.store;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoreApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	public DataSource dataSource;

	@Test
	public void getConnection() throws SQLException {
		Connection conn = dataSource.getConnection();
		System.err.println(conn+"1111");
		
	}
	
	@Test
	public void md5() {
		String sal ="789";
		String password ="1234";
		String md5Password = DigestUtils.md5DigestAsHex((sal+password).getBytes());
		System.err.println(md5Password);
		// 1234
		// 81dc9bdb52d04dc20036dbd8313ed055
		// ╪сян 9b7e19b4707921c5960e7f1126e69a6c
	}
	
	@Test
	public void uuid() {
		for (int i = 0; i < 20; i++) {
			System.err.println(UUID.randomUUID().toString());
		}
	}
	
	@Test
	public void md5x() {
		String password ="123456";
		for (int i = 0; i < 5; i++) {
			password = DigestUtils.md5DigestAsHex(password.getBytes());
			System.err.println(password);
		}
	}
}
