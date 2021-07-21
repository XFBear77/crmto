package cn.tedu.store.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.User;
import cn.tedu.store.service.ex.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

	@Autowired
	private IUserService service;

	@Test
	public void reg() {
		try{
			User user = new User();
			user.setUsername("root");
			user.setPassword("1234");
			service.reg(user);
			System.err.println("OK");
		} catch(ServiceException e) {
			//���׳���������ʾ����
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void login() {
		try{
			String username ="root";
			String password="1234";
			User result = service.login(username, password);
			System.err.println(result);
		} catch(ServiceException e) {
			//���׳���������ʾ����
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void changePassword() {
		try{
			Integer uid = 8;
			String username ="�������Ա";
			String oldPassword = "888888";
			String password="1234";
			service.changePassword(uid, username, oldPassword, password);
			System.err.println("OK");
		} catch(ServiceException e) {
			//���׳���������ʾ����
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void chageAvatar() {
		try{
			Integer uid = 8;
			String username ="ͷ�����Ա";
			String avatar="ҵ�����µ�ͷ��·��";
			service.chageAvatar(uid, username, avatar);
			System.err.println("OK");
		} catch(ServiceException e) {
			//���׳���������ʾ����
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void changeInfo() {
		try{
			Integer uid = 8;
			String username ="���Ϲ���Ա";
			User user = new User();
			user.setGender(1);
			user.setPassword("13031454672");
			user.setEmail("root@163.com");
			service.changeInfo(uid, username, user);
			System.err.println("OK");
		} catch(ServiceException e) {
			//���׳���������ʾ����
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void getByUid() {
		try{
			Integer uid=8;
			User result = service.getByUid(uid);
			System.err.println(result);
		} catch(ServiceException e) {
			//���׳���������ʾ����
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
}
