package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Address;

/**
 * 处理收货地址数据的持久层接口
 */
public interface AddressMapper {

	/**
	 * 增加收货地址
	 * 
	 * @param address收货地址的数据
	 * @return 返回受影响行数
	 */
	Integer save(Address address);
	
	/**
	 * 根据收货地址的id删除收货地址
	 * @param aid  收货地址id
	 * @return 返回受影响的行数
	 */
	Integer deleteByAid(Integer aid);
	
	/**
	 * 将指定的收货地址设置为默认的
	 * @param aid  收货地址id
	 * @param modifideUser  最后修改执行人
	 * @param modifideTime  最后修改时间
	 * @return 受影响的行数
	 */
	Integer updateDafault(@Param("aid") Integer aid, @Param("modifideUser") String modifideUser,
			@Param("modifideTime") Date modifideTime);

	/**
	 * 将某用户的所有收货地址设置为非默认的
	 * @param uid 用户的id
	 * @return 返回修改后的地址
	 */
	Integer updateNonDafault(Integer uid);

	/**
	 * 根据用户id统计该用户收货地址的数量
	 * @param uid 用户的id
	 * @return 返回受影响的行数
	 */
	Integer countByUid(Integer uid);

	/**
	 * 查询某用户的收货地址列表
	 * @param uid  用户的id
	 * @return 返回该用户的收货地址列表
	 */
	List<Address> findByUid(Integer uid);

	/**
	 * 根据收货地址的id查询收货地址详情
	 * @param aid 收货地址id'
	 * @return 匹配的收货地址详情，如果没有匹配的数据，则返回null
	 */
	Address findByAid(Integer aid);
	
	/**
	 * 查询某用户最后修改1条收货地址
	 * @param uid 用户的id
	 * @return 该用户的最后修改1条收货地址
	 */
	Address findLastModifide(Integer uid);
}
