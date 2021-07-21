package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Address;
/**
 * �����ջ���ַ���ݵ�ҵ���ӿ�
 */
public interface IAddressService {
	/**
	 * ÿ���û�������������ջ���ַ���ݵ�����
	 */
	int MAX_COUNT=20;
	/**
	 * �����µ��ջ���ַ����
	 * @param address �ͻ����ύ���ջ���ַ����
	 * @param uid ��ǰ��¼���û���id
	 * @param username ��ǰ��¼���û���
	 */
	void addnew(Address address,Integer uid,String username);
	
	/**
	 * ɾ���ջ���ַ����
	 * @param aid ��ɾ���ջ���ַ���ݵ�id
	 * @param uid ��ǰ��¼���û���id
	 * @param username ��ǰ��¼���û���
	 */
	void delete(Integer aid, Integer uid ,String username);
	
	/**
	 * ��ѯĳ�û����ջ���ַ�б�
	 * @param uid �û���id
	 * @return ���ظ��û����ջ���ַ�б�
	 */
	List<Address> getByUid(Integer uid);
	
	/**
	 * ��ָ�����ջ���ַ����ΪĬ��
	 * @param aid �ջ���ַ���ݵ�id
	 * @param uid ��ǰ��¼���û���id
	 * @param username ��ǰ��¼���û���
	 */
	void setDefault(Integer aid,Integer uid,String username);
	
	/**
	 * �����ջ���ַ��id��ѯ�ջ���ַ����
	 * @param aid �ջ���ַid'
	 * @return ƥ����ջ���ַ���飬���û��ƥ������ݣ����׳�AddressNotFoundExcetpion
	 */
	Address getByAid(Integer aid);

}
