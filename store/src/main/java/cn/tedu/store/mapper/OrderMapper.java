package cn.tedu.store.mapper;

import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;

/**
 * ���������ݺͶ�����Ʒ���ݵĳ־ò�ӿ�
 */
public interface OrderMapper {
	/**
	 * ���붩������
	 * @param order ��������
	 * @return ������Ӱ�������
	 */
	Integer saveOrder(Order order);
	
	/**
	 * ���붩����Ʒ����
	 * @param orderItem ������Ʒ
	 * @return ������Ӱ�������
	 */
	Integer saveOredrItem(OrderItem orderItem);
	
}
