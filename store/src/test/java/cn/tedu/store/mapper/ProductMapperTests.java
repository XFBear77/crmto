package cn.tedu.store.mapper;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Product;



@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductMapperTests {

	@Autowired
	private ProductMapper mapper;
	
	@Test
	public void findHosList() {
		List<Product> result = mapper.findHosList();
		System.err.println(result.size());
		for (Product product : result) {
			System.err.println(product);
		}
	}

	@Test
	public void findById() {
		Integer id=100000425;
		Product result = mapper.findById(id);
		System.err.println(result);
	}

}
