package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.District;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistrictServiceTests {

	@Autowired
	private IDistrictService service;

	@Test
	public void getByParent() {
		String parent = "86";
		List<District> rows = service.getByParent(parent);
		System.err.println("count=" + rows.size());
		for (District district : rows) {
			System.err.println(district);
		}
	}

	@Test
	public void getByCode() {
		String code = "110000";
		District result = service.getByCode(code);
		System.err.println(result);

	}

	@Test
	public void a() {
		String code = "110000";
		District result = service.getByCode(code);
		System.err.println(result);

	}
}
