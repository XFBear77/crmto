package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.ex.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceTests {

	@Autowired
	private IAddressService service;

	@Test
	public void addnew() {
		try {
			Address address = new Address();
			address.setName("Kitty");
			Integer uid = 2;
			String username = "数据管理员";
			service.addnew(address, uid, username);
			System.err.println("ok.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void getUid() {
		try {
			Integer uid=8;
			List<Address> result = service.getByUid(uid);
			System.err.println(result.size());
			for (Address address : result) {
				System.err.println(address);
			}
			System.err.println("ok.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void setDefault() {
		try {
			Integer aid =26;
			Integer uid = 8;
			String username = "数据管理员";
			service.setDefault(aid, uid, username);
			System.err.println("ok.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void delete() {
		try {
			Integer aid =33;
			Integer uid = 8;
			String username = "数据管理员";
			service.delete(aid, uid, username);
			System.err.println("ok.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	
}
