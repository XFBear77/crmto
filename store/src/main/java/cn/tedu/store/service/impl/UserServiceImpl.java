package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.tedu.store.entity.User;
import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameConflictException;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;

	// 注册
	@Override
	public void reg(User user) {
		// 从参数user对象中获取用户名
		String username = user.getUsername();
		// 调用userMapper根据用户名查询用户数据
		User result = userMapper.findByUsername(username);
		// 判断查询结果是否不为null(条件写成抛出异常的代码)
		if (result != null) {
			// 是：用户名已存在，不允许注册，抛出UsernameConflictException异常
			throw new UsernameConflictException("用户名(" + username + ")已经被占用");
		}

		// 参数user是客户端提交的注册数据，并不包含隐藏数据
		// 执行加密
		String salt = UUID.randomUUID().toString();
		String md5Password = getMd5Password(user.getPassword(), salt);
		// 补全数据：salt
		user.setSalt(salt);
		// 补全数据：加密后的密码
		user.setPassword(md5Password);
		// 补全数据：is_delete,值为0
		user.setIsDelete(0);
		// 补全数据：4项日志，用户名为注册的用户名，时间为当前时间
		Date now = new Date();
		user.setCreatedUser(username);
		user.setCreatedTime(now);
		user.setModifideUser(username);
		user.setModifideTime(now);
		// 调用userMapper的功能插入用户数据，实现注册,并获取插入数据的时的返回值
		Integer rows = userMapper.save(user);
		// 判断以上返回值是否不为1
		if (rows != 1) {
			// 是：插入用户数据失败，抛出InsertException
			throw new InsertException("插入用户时出现未知错误");
		}

	}

	// 登录
	@Override
	public User login(String username, String password) {
		// 根据参数username查询用户数据
		User result = userMapper.findByUsername(username);
		// 判断查询结果是否为null
		if (result == null) {
			// 是：用户名不存在，抛出UserNotFoundException
			throw new UserNotFoundException("用户名不存在");
		}

		// 判断查询结果中的isDelete是否为1
		if (result.getIsDelete().equals(1)) {
			// 是:用户被标记为已删除,抛出UserNotFoundException
			throw new UserNotFoundException("用户数据已被删除");
		}

		// 从查询结果中取出盐值
		String salt = result.getSalt();
		// 调用getMd5Passowrd()，基于参数password和盐值进行加密
		String md5Password = getMd5Password(password, salt);
		// 判断加密后的密码于查询结果中的密码是否不匹配
		if (!result.getPassword().equals(md5Password)) {
			// 是:密码错误，抛出PasswordNotMatchException
			throw new PasswordNotMatchException("密码错误");
		}
		// 将查询结果中不应该响应给客户端的字段设置为null
		result.setIsDelete(null);
		result.setCreatedUser(null);
		result.setCreatedTime(null);
		result.setModifideUser(null);
		result.setModifideTime(null);
		result.setSalt(null);
		// 例如：isDelete、createdUser、createdTime、modifiedUser、modeifiedTime、password、salt
		// 返回查询结果
		return result;
	}

	// 修改密码
	@Override
	public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
		// 根据参数uid查询用户数据
		User result = userMapper.findByUid(uid);
		// 判断查询结果是否为null
		if (result == null) {
			// 是:用户数据不存在,抛出UserNotFoundException
			throw new UserNotFoundException("用户数据不存在");
		}
		// 判断查询结果中的isDelete是否为1
		if (result.getIsDelete().equals(1)) {
			// 是:用户标记为已删除,抛出UserNotFoundException
			throw new UserNotFoundException("用户数据已被删除");
		}
		// 取出查询结果中的盐值
		String salt = result.getSalt();
		// 将参数oldPassword进行加密,得到oldMd5Password
		String oldMd5Password = getMd5Password(oldPassword, salt);
		// 判断查询结果中的password于oldMd5Password是否不匹配
		if (!result.getPassword().equals(oldMd5Password)) {
			// 是:密码验证失败,抛出PasswordNotMatchException
			throw new PasswordNotMatchException("原密码错误");
		}
		// 将参数newPassword进行加密 ,得到newMd5Password
		String newMd5Password = getMd5Password(newPassword, salt);
		// 执行更新密码，获取返回值
		Integer rows = userMapper.updatePassword(uid, newMd5Password, username, new Date());
		// 判断返回的受影响的行数是否不为1
		if (rows != 1) {
			// 是:更新失败,抛出UpdateException
			throw new UpdateException("更新数据出现未知错误");
		}
	}
	
	//修改头像
	@Override
	public void chageAvatar(Integer uid, String username, String avatar) {
		//根据参数的uid查询数据
		User result = userMapper.findByUid(uid);
		//判断查询结果是否null --UserNotFoundException
		if(result ==null) {
			throw new UserNotFoundException("用户数据不存在");
		}
		//判断查询结果中的isDelete是否为1 --UserNotFoundException
		if(result.getIsDelete().equals(1)) {
			throw new UserNotFoundException("用户数据已被删除");
		}
		//执行修改头像
		Integer rows = userMapper.updateAvatar(uid, avatar, username, new Date());
		//判断返回值是否不为1--UpdateException
		if(rows !=1) {
			throw new UpdateException("更新数据出现未知错误");
		}
	}

	
	//修改资料
	@Override
	public void changeInfo(Integer uid, String username, User user) {
		//通过参数uid查询用户数据
		User result = userMapper.findByUid(uid);
		//判断查询结果是否为null
		if(result ==null) {
		//是：用户数据不存在，抛出UsernotFoundException
			throw new UserNotFoundException("用户数据不存在");
		}
		//判断查询结果中的isDelete是否为1
		if(result.getIsDelete().equals(1)) {
		//是：用户数据被标记为删除，抛出UsernotFoundException
			throw new UserNotFoundException("用户数据已被删除");
		}
		//将参数uid封装到参数user中
		user.setUid(uid);
		//将参数username封装到参数user的modifideUser中
		user.setUsername(username);
		//创建时间，封装到参数user的modifideTime中
		user.setModifideTime(new Date());
		//执行修改 ：userMapper.updateInfo（user）
		Integer rows = userMapper.updateInfo(user);
		//判断返回受影响的行数是否不为1
		if(rows !=1) {
		//是：更新错误，抛出UpdateException
			throw new UpdateException("更新数据出现未知错误");
		}	
	}
	

	//查询用户数据便于修改资料
	@Override
	public User getByUid(Integer uid) {
		// 通过参数uid查询用户数据
		User result = userMapper.findByUid(uid);
		// 判断查询结果是否为null
		if (result == null) {
			// 是：用户数据不存在，抛出UsernotFoundException
			throw new UserNotFoundException("用户不存在");
		}
		// 判断查询结果中的isDelete是否为1
		if (result.getIsDelete().equals(1)) {
			// 是：用户数据被标记为删除，抛出UsernotFoundException
			throw new UserNotFoundException("用户数据已删除");
		}
		// 创建新的User对象
		User user = new User();
		// 将查询结果中的username,gender,phone,email封装到新对象中
		user.setUsername(result.getUsername());
		user.setGender(result.getGender());
		user.setPhone(result.getPhone());
		user.setEmail(result.getEmail());
		// 返回新对象
		return user;
	}
	
	
	

	/**
	 * 执行密码加密
	 * 
	 * @param password
	 *            原密码
	 * @param salt
	 *            盐值
	 * @return 加密后的密码
	 */
	private String getMd5Password(String password, String salt) {
		// 加密规则：在原密码的左侧和右侧均拼接盐值，循环加密5次
		String str = salt + password + salt;
		for (int i = 0; i < 5; i++) {
			str = DigestUtils.md5DigestAsHex(str.getBytes());
		}
		return str;
	}

}
