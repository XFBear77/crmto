package cn.tedu.store.service;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.vo.CartVO;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTests {

	@Autowired
	private ICartService  service ;
	
	@Test
	public void addnew() {
		try {
			Integer uid =3;
			Integer pid =10000017;
			Integer num =1;
			String username ="HAHAHA";
			service.addToCat(uid, pid, num, username);
			System.err.println("ok.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void getByUid() {
		Integer uid =8;
		List<CartVO> list = service.getByUid(uid);
		System.err.println(list.size());
		for (CartVO cartVO : list) {
			System.err.println(cartVO);
		}
	}
	
	@Test
	public void addNum() {
		try {
			Integer cid = 8;
			Integer uid = 8;
			String username = "HAHAHA";
			service.addNum(cid, uid, username);
			System.err.println("ok.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void getByCids() {
		Integer[] cids= {6,7,8,9};
		Integer uid =8;
		List<CartVO> list = service.getByCids(cids,uid );
		System.err.println(list.size());
		for (CartVO cartVO : list) {
			System.err.println(cartVO);
		}
	}
	
}
