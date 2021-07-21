package cn.tedu.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.Product;
import cn.tedu.store.mapper.ProductMapper;
import cn.tedu.store.service.IProductService;
import cn.tedu.store.service.ex.ProductNotFoundException;

/**
 * ������Ʒ���ݵ�ҵ���ʵ����
 */
@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	public ProductMapper productMapper;

	@Override
	public List<Product> getHostList() {
		List<Product> list = findHosList();
		for (Product product : list) {
			product.setCategoryId(null);
			product.setStatus(null);
			product.setPriority(null);
			product.setCreatedUser(null);
			product.setCreatedTime(null);
			product.setModifideUser(null);
			product.setModifideTime(null);
		}
		return list;
	}
	
	@Override
	public Product getById(Integer id) {
	
		Product product = findById(id);
		if(product ==null) {
			throw new ProductNotFoundException("���Է��ʵ���Ʒ���ݲ�����");
		}
		product.setCategoryId(null);
		product.setStatus(null);
		product.setPriority(null);
		product.setCreatedUser(null);
		product.setCreatedTime(null);
		product.setModifideUser(null);
		product.setModifideTime(null);
		return product;
	}

	/**
	 * ��ѯ��������Ʒ���е�ǰ4����Ʒ����
	 * 
	 * @return ���ز�ѯ��������Ʒ���е�ǰ4����Ʒ����
	 */
	private List<Product> findHosList() {
		return productMapper.findHosList();
	}
	
	/**
	 * ������Ʒid��ѯ��Ʒ����
	 * @param id ��Ʒid
	 * @return ƥ�����Ʒ���飬���û��ƥ�����ݣ��򷵻�null
	 */
	Product findById(Integer id) {
		return productMapper.findById(id);
	}
	

}
