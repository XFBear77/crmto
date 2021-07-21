package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.District;
import cn.tedu.store.mapper.AddressMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.IDistrictService;
import cn.tedu.store.service.ex.AccessDenidException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.AddressSizeLimitException;
import cn.tedu.store.service.ex.DeleteException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;

@Service
public class AddressrServiceImpl implements IAddressService {

	@Autowired
	public AddressMapper mapper;

	@Autowired
	public IDistrictService districtService;

	@Override
	public void addnew(Address address, Integer uid, String username) {
		// 根据参数uid获取该用户的收货地址的数据的数量
		Integer count = countByUid(uid);
		// 判断数量是否大于或等于限制值
		if (count >= MAX_COUNT) {
			// 是：抛出AddressSizeLimitException
			throw new AddressSizeLimitException("收货地址数量达到上限!");
		}
		// 判断收货地址的数量值是否为0
		// 是：即将插入的收货地址是默认的，isDefault=1
		// 否：即将插入的收货地址不是默认的 isDefault=0
		Integer isDefault = count == 0 ? 1 : 0;
		// 补全数据：将isDefault封装到address中
		address.setIsDafault(isDefault);
		// 补全数据：将参数封装uid封装到address中
		address.setUid(uid);
		// 补全数据：将参数username封装为参数createdUser和modifideUse属性
		address.setCreatedUser(username);
		address.setModifideUser(username);
		// 补全数据：创建当前时间对象，封装为参数address中的createdTime和modifideTime属性值
		Date now = new Date();
		address.setCreatedTime(now);
		address.setModifideTime(now);
		// 补全数据：省、市、区的名称
		address.getProvinceCode();
		District province = districtService.getByCode(address.getProvinceCode());
		// System.err.println(province);
		if (province != null) {
			address.setProvinceName(province.getName());
		}
		District city = districtService.getByCode(address.getCityCode());
		if (city != null) {
			address.setCityName(city.getName());
		}
		District area = districtService.getByCode(address.getAreaCode());
		if (area != null) {
			address.setAreaName(area.getName());
		}
		// 执行插入数据，并获取返回值
		save(address);
	}

	
	@Override
	@Transactional
	public void setDefault(Integer aid, Integer uid, String username) {
		// 根据参数aid查询收货地址数据
		Address result = findByAid(aid);
		// 判断查询结果是否为null
		if (result == null) {
			// 是：抛出AddressNotFoundException
			throw new AddressNotFoundException("收货地址数据不存在，可能已经被删除");
		}
		// 判断查询结果中的uid和参数uid是否不一致，使用equals（）而不要使用！=
		if (!result.getUid().equals(uid)) {
			// 是：AccessDeniedException
			throw new AccessDenidException("非法访问");
		}
		// 将当前用户的所有收货地址设置为非默认
		updateNonDafault(uid);
		// 将指定的收货地址设为默认
		updateDafault(aid, username, new Date());
	}

	@Override
	@Transactional
	public void delete(Integer aid, Integer uid, String username) {
		// 根据参数aid查询收货地址数据
		Address result = findByAid(aid);
		// 判断查询结果是否为null：AddressNotFoundException
		if (result == null) {
			throw new AddressNotFoundException("尝试访问的收货地址数据不存在，可能被删除");
		}
		// 判断结果中的uid与参数uid是否不配：AccessDeniedException
		if (!result.getUid().equals(uid)) {
			throw new AccessDenidException("拒绝访问，例如：查询数据归属错误");
		}
		// 执行删除，并获取返回值
		deleteByAid(aid);

		// 判断查询结果中的isDefault是否为0
		if (result.getIsDafault().equals(0)) {
			// 删除的不是默认收货地址，可直接结束：return；
			return;
		}

		// 调用countByUid(uid)统计当前用户的收货地址数量
		Integer count = countByUid(uid);
		// 判断数量是否为0
		if (count == 0) {
			// 当前用户已经没有收货地址，可直接结束：return
			return;
		}

		// 调用findLastModified(uid)找出最后修改的收货地址
		Address find = findLastModifide(uid);
		// 基于以上查询结果中的aid，将该收货地址设置为默认，并获取返回值
		Integer fAid = find.getAid();
		updateDafault(fAid, username, new Date());

	}
	
	@Override
	public List<Address> getByUid(Integer uid) {
		List<Address> list = findByUid(uid);
		for (Address address : list) {
			address.setCreatedUser(null);
			address.setCreatedTime(null);
			address.setModifideUser(null);
			address.setModifideTime(null);
		}
		return list;
	}

	
	@Override
	public Address getByAid(Integer aid) {
		Address result = findByAid(aid);
		if(result ==null) {
			throw new AddressNotFoundException("尝试访问的收货数据不存在！");
		}
		result.setCreatedTime(null);
		result.setCreatedUser(null);
		result.setModifideTime(null);
		result.setModifideUser(null);
		return  result;
	}

	
	/**
	 * 增加收货地址
	 * @param address收货地址的数据
	 */
	private void save(Address address) {
		Integer rows = mapper.save(address);
		// 判断返回值是否不为1
		if (rows != 1) {
			// 是：抛出InsertException
			throw new InsertException("收货地址数据时出现未知错误");
		}
	}

	/**
	 * 根据收货地址的id删除收货地址
	 * @param aid   收货地址id
	 */
	private void deleteByAid(Integer aid) {
		// 执行删除，并获取返回值
		Integer rows = mapper.deleteByAid(aid);
		// 判断受影响行数是否不为1：DeleteException
		if (rows != 1) {
			throw new DeleteException("删除收货地址时，发生未知错误");
		}
	}

	/**
	 * 将指定的收货地址设置为默认的
	 * @param aid  收货地址id
	 * @param modifideUser  最后修改执行人
	 * @param modifideTime  最后修改时间
	 */
	private void updateDafault(Integer aid, String modifideUser, Date modifideTime) {
		// 将指定的收货地址设为默认
		Integer rows = mapper.updateDafault(aid, modifideUser, modifideTime);
		if (rows != 1) {
			throw new UpdateException("更新数据出现未知错误[1]");
		}
	}

	/**
	 * 将某用户的所有收货地址设置为非默认的
	 * @param uid 用户的id
	 */
	private void updateNonDafault(Integer uid) {
		// 将当前用户的所有收货地址设置为非默认
		Integer rows = mapper.updateNonDafault(uid);
		if (rows < 1) {
			throw new UpdateException("更新数据出现未知错误[1]");
		}
	}

	/**
	 * 根据用户id统计该用户收货地址的数量
	 * @param uid 用户的id
	 */
	private Integer countByUid(Integer uid) {
		return mapper.countByUid(uid);
	}

	/**
	 * 查询某用户的收货地址列表
	 * @param uid 用户的id
	 * @return 返回该用户的收货地址列表
	 */
	private List<Address> findByUid(Integer uid) {
		return mapper.findByUid(uid);
	}

	/**
	 * 根据收货地址的id查询收货地址详情
	 * @param aid 收货地址id'
	 * @return 匹配的收货地址详情，如果没有匹配的数据，则返回null
	 */
	private Address findByAid(Integer aid) {
		return mapper.findByAid(aid);
	}

	/**
	 * 查询某用户最后修改1条收货地址
	 * @param uid 用户的id
	 * @return 该用户的最后修改1条收货地址
	 */
	private Address findLastModifide(Integer uid) {
		return mapper.findLastModifide(uid);
	}

	
}
