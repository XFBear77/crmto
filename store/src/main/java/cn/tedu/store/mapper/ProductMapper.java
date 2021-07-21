package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.Product;
/**
 * ������Ʒ���ݵĳ־ò�ӿ�
 */
public interface ProductMapper {
	/**
	 * ��ѯ��������Ʒ���е�ǰ4����Ʒ����
	 * @return ���ز�ѯ��������Ʒ���е�ǰ4����Ʒ����
	 */
	List<Product> findHosList();
	
	/**
	 * ������Ʒid��ѯ��Ʒ����
	 * @param id ��Ʒid
	 * @return ƥ�����Ʒ���飬���û��ƥ�����ݣ��򷵻�null
	 */
	Product findById(Integer id);
}
