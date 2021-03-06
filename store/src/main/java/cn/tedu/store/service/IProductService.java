package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Product;
/**
 * 处理商品数据的业务层接口
 */
public interface IProductService {
	/**
	 * 查询热销的商品排行的前4个商品数据
	 * @return 返回查询热销的商品排行的前4个商品数据
	 */
	List<Product> getHostList();
	
	/**
	 * 根据商品id查询商品详情
	 * @param id 商品id
	 * @return 匹配的商品详情，如果没有匹配数据，则抛出ProductNotFoundException异常
	 */
	Product getById(Integer id);
}
