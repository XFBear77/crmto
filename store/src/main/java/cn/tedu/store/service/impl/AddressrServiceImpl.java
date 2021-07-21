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
		// ���ݲ���uid��ȡ���û����ջ���ַ�����ݵ�����
		Integer count = countByUid(uid);
		// �ж������Ƿ���ڻ��������ֵ
		if (count >= MAX_COUNT) {
			// �ǣ��׳�AddressSizeLimitException
			throw new AddressSizeLimitException("�ջ���ַ�����ﵽ����!");
		}
		// �ж��ջ���ַ������ֵ�Ƿ�Ϊ0
		// �ǣ�����������ջ���ַ��Ĭ�ϵģ�isDefault=1
		// �񣺼���������ջ���ַ����Ĭ�ϵ� isDefault=0
		Integer isDefault = count == 0 ? 1 : 0;
		// ��ȫ���ݣ���isDefault��װ��address��
		address.setIsDafault(isDefault);
		// ��ȫ���ݣ���������װuid��װ��address��
		address.setUid(uid);
		// ��ȫ���ݣ�������username��װΪ����createdUser��modifideUse����
		address.setCreatedUser(username);
		address.setModifideUser(username);
		// ��ȫ���ݣ�������ǰʱ����󣬷�װΪ����address�е�createdTime��modifideTime����ֵ
		Date now = new Date();
		address.setCreatedTime(now);
		address.setModifideTime(now);
		// ��ȫ���ݣ�ʡ���С���������
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
		// ִ�в������ݣ�����ȡ����ֵ
		save(address);
	}

	
	@Override
	@Transactional
	public void setDefault(Integer aid, Integer uid, String username) {
		// ���ݲ���aid��ѯ�ջ���ַ����
		Address result = findByAid(aid);
		// �жϲ�ѯ����Ƿ�Ϊnull
		if (result == null) {
			// �ǣ��׳�AddressNotFoundException
			throw new AddressNotFoundException("�ջ���ַ���ݲ����ڣ������Ѿ���ɾ��");
		}
		// �жϲ�ѯ����е�uid�Ͳ���uid�Ƿ�һ�£�ʹ��equals��������Ҫʹ�ã�=
		if (!result.getUid().equals(uid)) {
			// �ǣ�AccessDeniedException
			throw new AccessDenidException("�Ƿ�����");
		}
		// ����ǰ�û��������ջ���ַ����Ϊ��Ĭ��
		updateNonDafault(uid);
		// ��ָ�����ջ���ַ��ΪĬ��
		updateDafault(aid, username, new Date());
	}

	@Override
	@Transactional
	public void delete(Integer aid, Integer uid, String username) {
		// ���ݲ���aid��ѯ�ջ���ַ����
		Address result = findByAid(aid);
		// �жϲ�ѯ����Ƿ�Ϊnull��AddressNotFoundException
		if (result == null) {
			throw new AddressNotFoundException("���Է��ʵ��ջ���ַ���ݲ����ڣ����ܱ�ɾ��");
		}
		// �жϽ���е�uid�����uid�Ƿ��䣺AccessDeniedException
		if (!result.getUid().equals(uid)) {
			throw new AccessDenidException("�ܾ����ʣ����磺��ѯ���ݹ�������");
		}
		// ִ��ɾ��������ȡ����ֵ
		deleteByAid(aid);

		// �жϲ�ѯ����е�isDefault�Ƿ�Ϊ0
		if (result.getIsDafault().equals(0)) {
			// ɾ���Ĳ���Ĭ���ջ���ַ����ֱ�ӽ�����return��
			return;
		}

		// ����countByUid(uid)ͳ�Ƶ�ǰ�û����ջ���ַ����
		Integer count = countByUid(uid);
		// �ж������Ƿ�Ϊ0
		if (count == 0) {
			// ��ǰ�û��Ѿ�û���ջ���ַ����ֱ�ӽ�����return
			return;
		}

		// ����findLastModified(uid)�ҳ�����޸ĵ��ջ���ַ
		Address find = findLastModifide(uid);
		// �������ϲ�ѯ����е�aid�������ջ���ַ����ΪĬ�ϣ�����ȡ����ֵ
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
			throw new AddressNotFoundException("���Է��ʵ��ջ����ݲ����ڣ�");
		}
		result.setCreatedTime(null);
		result.setCreatedUser(null);
		result.setModifideTime(null);
		result.setModifideUser(null);
		return  result;
	}

	
	/**
	 * �����ջ���ַ
	 * @param address�ջ���ַ������
	 */
	private void save(Address address) {
		Integer rows = mapper.save(address);
		// �жϷ���ֵ�Ƿ�Ϊ1
		if (rows != 1) {
			// �ǣ��׳�InsertException
			throw new InsertException("�ջ���ַ����ʱ����δ֪����");
		}
	}

	/**
	 * �����ջ���ַ��idɾ���ջ���ַ
	 * @param aid   �ջ���ַid
	 */
	private void deleteByAid(Integer aid) {
		// ִ��ɾ��������ȡ����ֵ
		Integer rows = mapper.deleteByAid(aid);
		// �ж���Ӱ�������Ƿ�Ϊ1��DeleteException
		if (rows != 1) {
			throw new DeleteException("ɾ���ջ���ַʱ������δ֪����");
		}
	}

	/**
	 * ��ָ�����ջ���ַ����ΪĬ�ϵ�
	 * @param aid  �ջ���ַid
	 * @param modifideUser  ����޸�ִ����
	 * @param modifideTime  ����޸�ʱ��
	 */
	private void updateDafault(Integer aid, String modifideUser, Date modifideTime) {
		// ��ָ�����ջ���ַ��ΪĬ��
		Integer rows = mapper.updateDafault(aid, modifideUser, modifideTime);
		if (rows != 1) {
			throw new UpdateException("�������ݳ���δ֪����[1]");
		}
	}

	/**
	 * ��ĳ�û��������ջ���ַ����Ϊ��Ĭ�ϵ�
	 * @param uid �û���id
	 */
	private void updateNonDafault(Integer uid) {
		// ����ǰ�û��������ջ���ַ����Ϊ��Ĭ��
		Integer rows = mapper.updateNonDafault(uid);
		if (rows < 1) {
			throw new UpdateException("�������ݳ���δ֪����[1]");
		}
	}

	/**
	 * �����û�idͳ�Ƹ��û��ջ���ַ������
	 * @param uid �û���id
	 */
	private Integer countByUid(Integer uid) {
		return mapper.countByUid(uid);
	}

	/**
	 * ��ѯĳ�û����ջ���ַ�б�
	 * @param uid �û���id
	 * @return ���ظ��û����ջ���ַ�б�
	 */
	private List<Address> findByUid(Integer uid) {
		return mapper.findByUid(uid);
	}

	/**
	 * �����ջ���ַ��id��ѯ�ջ���ַ����
	 * @param aid �ջ���ַid'
	 * @return ƥ����ջ���ַ���飬���û��ƥ������ݣ��򷵻�null
	 */
	private Address findByAid(Integer aid) {
		return mapper.findByAid(aid);
	}

	/**
	 * ��ѯĳ�û�����޸�1���ջ���ַ
	 * @param uid �û���id
	 * @return ���û�������޸�1���ջ���ַ
	 */
	private Address findLastModifide(Integer uid) {
		return mapper.findLastModifide(uid);
	}

	
}
