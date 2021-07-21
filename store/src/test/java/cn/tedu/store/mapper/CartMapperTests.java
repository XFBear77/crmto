package cn.tedu.store.mapper;


import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.vo.CartVO;



@RunWith(SpringRunner.class)
@SpringBootTest
public class CartMapperTests {

	@Autowired
	private CartMapper mapper;
	
	@Test
	public void save() {
		Cart cart = new Cart();
		cart.setUid(1);
		cart.setPid(2);
		cart.setNum(3);
		cart.setPrice(4L);
		Integer rows = mapper.save(cart);
		System.err.println(rows);
	}
	
	@Test
	public void updateNum() {
		Integer cid =4;
		Integer num =10;
		String modifideUser = "admin";
		Date modifideTime =new Date();
		Integer rows = mapper.updateNum(cid, num, modifideUser, modifideTime);
		System.err.println(rows);
	}
	
	@Test
	public void findUidAndPid() {
		Integer uid =1;
		Integer pid =2;
		Cart result = mapper.findUidAndPid(uid, pid);
		System.err.println(result);
	}
	
	@Test
	public void findByUid() {
		Integer uid =8;
		List<CartVO> list = mapper.findByUid(uid);
		System.err.println(list.size());
		for (CartVO cartVO : list) {
			System.err.println(cartVO);
		}
	}
	
	@Test
	public void findByCid() {
		Integer cid= 8;
		Cart result = mapper.findByCid(cid);
		System.err.println(result);
		
	}
	
	@Test
	public void findByCids() {
		Integer[] cids = {8,7,6,9};
		List<CartVO> list = mapper.findByCids(cids);
		for (CartVO cartVO : list) {
			System.err.println(cartVO);
		}
	}
}
