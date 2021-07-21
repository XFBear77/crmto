package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Product;
/**
 * ������Ʒ���ݵ�ҵ���ӿ�
 */
public interface IProductService {
	/**
	 * ��ѯ��������Ʒ���е�ǰ4����Ʒ����
	 * @return ���ز�ѯ��������Ʒ���е�ǰ4����Ʒ����
	 */
	List<Product> getHostList();
	
	/**
	 * ������Ʒid��ѯ��Ʒ����
	 * @param id ��Ʒid
	 * @return ƥ�����Ʒ���飬���û��ƥ�����ݣ����׳�ProductNotFoundException�쳣
	 */
	Product getById(Integer id);
}
