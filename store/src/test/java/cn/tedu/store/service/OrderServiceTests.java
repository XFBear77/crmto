package cn.tedu.store.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.service.ex.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTests {

	@Autowired
	private IOrderService service;

	@Test
	public void createOrder() {
		try {
			Integer aid = 23;
			Integer[] cids = {6,7,8,9};
			Integer uid =8;
			String username ="HAHAHA";
			service.createOrder(aid, cids, uid, username);
			System.err.println("ok.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	
	
	
}
