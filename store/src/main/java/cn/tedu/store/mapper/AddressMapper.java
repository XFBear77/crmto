package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Address;

/**
 * �����ջ���ַ���ݵĳ־ò�ӿ�
 */
public interface AddressMapper {

	/**
	 * �����ջ���ַ
	 * 
	 * @param address�ջ���ַ������
	 * @return ������Ӱ������
	 */
	Integer save(Address address);
	
	/**
	 * �����ջ���ַ��idɾ���ջ���ַ
	 * @param aid  �ջ���ַid
	 * @return ������Ӱ�������
	 */
	Integer deleteByAid(Integer aid);
	
	/**
	 * ��ָ�����ջ���ַ����ΪĬ�ϵ�
	 * @param aid  �ջ���ַid
	 * @param modifideUser  ����޸�ִ����
	 * @param modifideTime  ����޸�ʱ��
	 * @return ��Ӱ�������
	 */
	Integer updateDafault(@Param("aid") Integer aid, @Param("modifideUser") String modifideUser,
			@Param("modifideTime") Date modifideTime);

	/**
	 * ��ĳ�û��������ջ���ַ����Ϊ��Ĭ�ϵ�
	 * @param uid �û���id
	 * @return �����޸ĺ�ĵ�ַ
	 */
	Integer updateNonDafault(Integer uid);

	/**
	 * �����û�idͳ�Ƹ��û��ջ���ַ������
	 * @param uid �û���id
	 * @return ������Ӱ�������
	 */
	Integer countByUid(Integer uid);

	/**
	 * ��ѯĳ�û����ջ���ַ�б�
	 * @param uid  �û���id
	 * @return ���ظ��û����ջ���ַ�б�
	 */
	List<Address> findByUid(Integer uid);

	/**
	 * �����ջ���ַ��id��ѯ�ջ���ַ����
	 * @param aid �ջ���ַid'
	 * @return ƥ����ջ���ַ���飬���û��ƥ������ݣ��򷵻�null
	 */
	Address findByAid(Integer aid);
	
	/**
	 * ��ѯĳ�û�����޸�1���ջ���ַ
	 * @param uid �û���id
	 * @return ���û�������޸�1���ջ���ַ
	 */
	Address findLastModifide(Integer uid);
}
