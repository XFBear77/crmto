package cn.tedu.store.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.User;

/**
 * 处理用户数据的持久层接口
 */
public interface UserMapper {
	
	/**
	 * 插入用户数据
	 * @param user 用户数据
	 * @return 受影响的行数
	 */
	Integer save(User user);
	
	/**
	 * 更新密码
	 * @param uid	用户id
	 * @param password 新密码
	 * @param modifiedUser	修改执行人
	 * @param modifiedTime	修改时间
	 * @return	受影响的行数
	 */
	Integer updatePassword(@Param("uid")Integer uid,
			@Param("password") String password, @Param("modifideUser") String modifiedUser,
			@Param("modifideTime") Date modifiedTime);
	
	/**
	 * 修改个人资料
	 * @param user 用户数据
	 * @return 返回受影响的行数
	 */
	Integer updateInfo(User user);
	
	/**
	 * 更新用户头像
	 * @param uid 用户名
	 * @param avatar 用户头像
	 * @param modifideUser 修改执行人
	 * @param modifideTime 修改时间
	 * @return 返回受影响的行数
	 */
	Integer updateAvatar(@Param("uid")Integer uid,@Param("avatar")String avatar,
			@Param("modifideUser")String modifideUser,@Param("modifideTime")Date modifideTime);
	/**
	 * 根据用户名查询用户数据
	 * @param uid 用户名id
	 * @return 匹配的用户数据, 如果没有匹配的数据,则返回null
	 */
	User findByUid(Integer uid);
	
	/**
	 * 根据用户名查询用户数据
	 * @param username  用户名
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	User findByUsername(String username);
}
