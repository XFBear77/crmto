package cn.tedu.store.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.District;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistrictMapperTests {

	@Autowired
	private DistrictMapper distric;

	@Test
	public void findByParent() {
		String parent = "86";
		List<District> rows = distric.findByParent(parent);
		System.err.println("count=" + rows.size());
		for (District district : rows) {
			System.err.println(district);
		}
	}

	@Test
	public void findByCode() {
		String code ="110000";
		District result = distric.findByCode(code);
		System.err.println(result);

	}
	
}
