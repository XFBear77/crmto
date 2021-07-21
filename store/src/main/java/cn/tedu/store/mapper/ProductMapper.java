package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.Product;
/**
 * 处理商品数据的持久层接口
 */
public interface ProductMapper {
	/**
	 * 查询热销的商品排行的前4个商品数据
	 * @return 返回查询热销的商品排行的前4个商品数据
	 */
	List<Product> findHosList();
	
	/**
	 * 根据商品id查询商品详情
	 * @param id 商品id
	 * @return 匹配的商品详情，如果没有匹配数据，则返回null
	 */
	Product findById(Integer id);
}
