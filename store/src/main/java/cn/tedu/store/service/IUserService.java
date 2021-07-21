package cn.tedu.store.service;

import cn.tedu.store.entity.User;

/**
 * �����û����ݵ�ҵ���ӿ�
 */
public interface IUserService {
	/**
	 * �û�ע��
	 * 
	 * @param user
	 *            ���û�����
	 */
	void reg(User user);

	/**
	 * �û���¼
	 * @param username �û���
	 * @param password ����
	 * @return �ɹ���¼���û�������
	 */
	User login(String username, String password);
	/**
	 * �޸�����
	 * @param uid �û�id
	 * @param username �û���
	 * @param oldPassword ������
	 * @param password	������
	 */
	void changePassword(Integer uid,String username,String oldPassword,String password);
	
	/**
	 * �޸�ͷ��
	 * @param uid �û�id
	 * @param username �û���
	 * @param avatar �û�ͷ��
	 */
	void chageAvatar(Integer uid,String username,String avatar);
	
	/**
	 * �޸ĸ�������
	 * @param uid �û���id
	 * @param username �û���
	 * @param user �µĸ�������
	 */
	void changeInfo(Integer uid,String username,User user);
	
	/**
	 * �����û�id��ѯ�û�����
	 * @param uid �û�id
	 * @return ƥ���û����ݣ����û��ƥ������ݣ�����null
	 */
	User getByUid(Integer uid);
	
	

}
