package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Address;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressMapperTests {

	@Autowired
	private AddressMapper mapper;

	@Test
	public void save() {
		Address address = new Address();
		address.setUid(1);
		address.setName("Lucy");
		Integer rows = mapper.save(address);
		System.err.println(rows);
	}

	@Test
	public void deleteByAid() {
		Integer aid = 31;
		Integer rows = mapper.deleteByAid(aid);
		System.err.println(rows);
	}

	@Test
	public void countByUid() {
		Integer uid = 1;
		Integer rows = mapper.countByUid(uid);
		System.err.println(rows);
	}

	@Test
	public void findByUid() {
		Integer uid = 8;
		List<Address> result = mapper.findByUid(uid);
		System.err.println(result.size());
		for (Address address : result) {
			System.err.println(address);
		}
	}

	@Test
	public void updateDafault() {
		Integer aid = 33;
		String modifideUser = "±à¼­";
		Date modifideTime = new Date();
		Integer rows = mapper.updateDafault(aid, modifideUser, modifideTime);
		System.err.println(rows);
	}

	@Test
	public void updateNonDefault() {
		Integer uid = 8;
		Integer rows = mapper.updateNonDafault(uid);
		System.err.println(rows);
	}

	@Test
	public void findByAid() {
		Integer aid = 33;
		Address result = mapper.findByAid(aid);
		System.err.println(result);
	}

	@Test
	public void findLastModifide() {
		Integer uid = 8;
		Address result = mapper.findLastModifide(uid);
		System.err.println(result);
	}

}
