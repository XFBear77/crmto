package cn.tedu.store.service;

import cn.tedu.store.entity.Order;

/**
 * ���������ݺͶ�����Ʒ���ݵ�ҵ���ӿ�
 */
public interface IOrderService {
	
	/**
	 * ��������
	 * @param aid �ջ���ַ����id
	 * @param cids ��������Ĺ��ﳵ����id
	 * @param uid ��ǰ��¼���û�id
	 * @param username  ��ǰ��¼���û���
	 * @return �����´����Ķ�������
	 */
	Order createOrder(Integer aid,Integer[] cids,Integer uid,String username);
}
