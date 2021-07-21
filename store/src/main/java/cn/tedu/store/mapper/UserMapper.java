package cn.tedu.store.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.User;

/**
 * �����û����ݵĳ־ò�ӿ�
 */
public interface UserMapper {
	
	/**
	 * �����û�����
	 * @param user �û�����
	 * @return ��Ӱ�������
	 */
	Integer save(User user);
	
	/**
	 * ��������
	 * @param uid	�û�id
	 * @param password ������
	 * @param modifiedUser	�޸�ִ����
	 * @param modifiedTime	�޸�ʱ��
	 * @return	��Ӱ�������
	 */
	Integer updatePassword(@Param("uid")Integer uid,
			@Param("password") String password, @Param("modifideUser") String modifiedUser,
			@Param("modifideTime") Date modifiedTime);
	
	/**
	 * �޸ĸ�������
	 * @param user �û�����
	 * @return ������Ӱ�������
	 */
	Integer updateInfo(User user);
	
	/**
	 * �����û�ͷ��
	 * @param uid �û���
	 * @param avatar �û�ͷ��
	 * @param modifideUser �޸�ִ����
	 * @param modifideTime �޸�ʱ��
	 * @return ������Ӱ�������
	 */
	Integer updateAvatar(@Param("uid")Integer uid,@Param("avatar")String avatar,
			@Param("modifideUser")String modifideUser,@Param("modifideTime")Date modifideTime);
	/**
	 * �����û�����ѯ�û�����
	 * @param uid �û���id
	 * @return ƥ����û�����, ���û��ƥ�������,�򷵻�null
	 */
	User findByUid(Integer uid);
	
	/**
	 * �����û�����ѯ�û�����
	 * @param username  �û���
	 * @return ƥ����û����ݣ����û��ƥ������ݣ��򷵻�null
	 */
	User findByUsername(String username);
}
