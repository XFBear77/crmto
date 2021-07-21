package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.vo.CartVO;
/**
 * 处理购物车的持久层接口
 */
public interface CartMapper {
	
	/** 向购物车表中插入数据
	 * 向购物车表中插入数据
	 * @param cart 购物车实体类
	 * @return 返回受影响的行数
	 */
	Integer save(Cart cart);
	
	/**
	 * 修改购物车表中商品的数量
	 * @param cid 购物车数据id
	 * @param num 新的数量
	 * @param modifideUser 修改执行人
	 * @param modifideTime 修改时间
	 * @return 返回受影响的行数
	 */
	Integer updateNum(@Param("cid")Integer cid,@Param("num")Integer num,
			@Param("modifideUser")String modifideUser,@Param("modifideTime")Date modifideTime);
	
	/**
	 * 根据购物车数据id查询购物车详情
	 * @param cid 购物车数据id
	 * @return  返回匹配的购物车详情，如果没有匹配数据，返回null
	 */
	Cart findByCid(Integer cid);
	
	/**
	 * 获取某用户在购物车中添加的某商品的数据
	 * @param uid 用户id
	 * @param pid 商品id
	 * @return 相关的购物车数据，如果没有匹配数据，返回null
	 */
	Cart findUidAndPid(@Param("uid")Integer uid,@Param("pid")Integer pid);
	
	/**
	 * 查询某用户的购物车列表
	 * @param uid 用户的id
	 * @return 返回用户购物车列表
	 */
	List<CartVO> findByUid(Integer uid);
	
	/**
	 * 根据若干个购物车数据查询数据
	 * @param cids 若干个购物车数据id
	 * @return 匹配的购物车数据的列表
	 */
	List<CartVO> findByCids(Integer[] cids);
	
	
}
