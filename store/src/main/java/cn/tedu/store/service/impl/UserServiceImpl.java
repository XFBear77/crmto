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

	// ע��
	@Override
	public void reg(User user) {
		// �Ӳ���user�����л�ȡ�û���
		String username = user.getUsername();
		// ����userMapper�����û�����ѯ�û�����
		User result = userMapper.findByUsername(username);
		// �жϲ�ѯ����Ƿ�Ϊnull(����д���׳��쳣�Ĵ���)
		if (result != null) {
			// �ǣ��û����Ѵ��ڣ�������ע�ᣬ�׳�UsernameConflictException�쳣
			throw new UsernameConflictException("�û���(" + username + ")�Ѿ���ռ��");
		}

		// ����user�ǿͻ����ύ��ע�����ݣ�����������������
		// ִ�м���
		String salt = UUID.randomUUID().toString();
		String md5Password = getMd5Password(user.getPassword(), salt);
		// ��ȫ���ݣ�salt
		user.setSalt(salt);
		// ��ȫ���ݣ����ܺ������
		user.setPassword(md5Password);
		// ��ȫ���ݣ�is_delete,ֵΪ0
		user.setIsDelete(0);
		// ��ȫ���ݣ�4����־���û���Ϊע����û�����ʱ��Ϊ��ǰʱ��
		Date now = new Date();
		user.setCreatedUser(username);
		user.setCreatedTime(now);
		user.setModifideUser(username);
		user.setModifideTime(now);
		// ����userMapper�Ĺ��ܲ����û����ݣ�ʵ��ע��,����ȡ�������ݵ�ʱ�ķ���ֵ
		Integer rows = userMapper.save(user);
		// �ж����Ϸ���ֵ�Ƿ�Ϊ1
		if (rows != 1) {
			// �ǣ������û�����ʧ�ܣ��׳�InsertException
			throw new InsertException("�����û�ʱ����δ֪����");
		}

	}

	// ��¼
	@Override
	public User login(String username, String password) {
		// ���ݲ���username��ѯ�û�����
		User result = userMapper.findByUsername(username);
		// �жϲ�ѯ����Ƿ�Ϊnull
		if (result == null) {
			// �ǣ��û��������ڣ��׳�UserNotFoundException
			throw new UserNotFoundException("�û���������");
		}

		// �жϲ�ѯ����е�isDelete�Ƿ�Ϊ1
		if (result.getIsDelete().equals(1)) {
			// ��:�û������Ϊ��ɾ��,�׳�UserNotFoundException
			throw new UserNotFoundException("�û������ѱ�ɾ��");
		}

		// �Ӳ�ѯ�����ȡ����ֵ
		String salt = result.getSalt();
		// ����getMd5Passowrd()�����ڲ���password����ֵ���м���
		String md5Password = getMd5Password(password, salt);
		// �жϼ��ܺ�������ڲ�ѯ����е������Ƿ�ƥ��
		if (!result.getPassword().equals(md5Password)) {
			// ��:��������׳�PasswordNotMatchException
			throw new PasswordNotMatchException("�������");
		}
		// ����ѯ����в�Ӧ����Ӧ���ͻ��˵��ֶ�����Ϊnull
		result.setIsDelete(null);
		result.setCreatedUser(null);
		result.setCreatedTime(null);
		result.setModifideUser(null);
		result.setModifideTime(null);
		result.setSalt(null);
		// ���磺isDelete��createdUser��createdTime��modifiedUser��modeifiedTime��password��salt
		// ���ز�ѯ���
		return result;
	}

	// �޸�����
	@Override
	public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
		// ���ݲ���uid��ѯ�û�����
		User result = userMapper.findByUid(uid);
		// �жϲ�ѯ����Ƿ�Ϊnull
		if (result == null) {
			// ��:�û����ݲ�����,�׳�UserNotFoundException
			throw new UserNotFoundException("�û����ݲ�����");
		}
		// �жϲ�ѯ����е�isDelete�Ƿ�Ϊ1
		if (result.getIsDelete().equals(1)) {
			// ��:�û����Ϊ��ɾ��,�׳�UserNotFoundException
			throw new UserNotFoundException("�û������ѱ�ɾ��");
		}
		// ȡ����ѯ����е���ֵ
		String salt = result.getSalt();
		// ������oldPassword���м���,�õ�oldMd5Password
		String oldMd5Password = getMd5Password(oldPassword, salt);
		// �жϲ�ѯ����е�password��oldMd5Password�Ƿ�ƥ��
		if (!result.getPassword().equals(oldMd5Password)) {
			// ��:������֤ʧ��,�׳�PasswordNotMatchException
			throw new PasswordNotMatchException("ԭ�������");
		}
		// ������newPassword���м��� ,�õ�newMd5Password
		String newMd5Password = getMd5Password(newPassword, salt);
		// ִ�и������룬��ȡ����ֵ
		Integer rows = userMapper.updatePassword(uid, newMd5Password, username, new Date());
		// �жϷ��ص���Ӱ��������Ƿ�Ϊ1
		if (rows != 1) {
			// ��:����ʧ��,�׳�UpdateException
			throw new UpdateException("�������ݳ���δ֪����");
		}
	}
	
	//�޸�ͷ��
	@Override
	public void chageAvatar(Integer uid, String username, String avatar) {
		//���ݲ�����uid��ѯ����
		User result = userMapper.findByUid(uid);
		//�жϲ�ѯ����Ƿ�null --UserNotFoundException
		if(result ==null) {
			throw new UserNotFoundException("�û����ݲ�����");
		}
		//�жϲ�ѯ����е�isDelete�Ƿ�Ϊ1 --UserNotFoundException
		if(result.getIsDelete().equals(1)) {
			throw new UserNotFoundException("�û������ѱ�ɾ��");
		}
		//ִ���޸�ͷ��
		Integer rows = userMapper.updateAvatar(uid, avatar, username, new Date());
		//�жϷ���ֵ�Ƿ�Ϊ1--UpdateException
		if(rows !=1) {
			throw new UpdateException("�������ݳ���δ֪����");
		}
	}

	
	//�޸�����
	@Override
	public void changeInfo(Integer uid, String username, User user) {
		//ͨ������uid��ѯ�û�����
		User result = userMapper.findByUid(uid);
		//�жϲ�ѯ����Ƿ�Ϊnull
		if(result ==null) {
		//�ǣ��û����ݲ����ڣ��׳�UsernotFoundException
			throw new UserNotFoundException("�û����ݲ�����");
		}
		//�жϲ�ѯ����е�isDelete�Ƿ�Ϊ1
		if(result.getIsDelete().equals(1)) {
		//�ǣ��û����ݱ����Ϊɾ�����׳�UsernotFoundException
			throw new UserNotFoundException("�û������ѱ�ɾ��");
		}
		//������uid��װ������user��
		user.setUid(uid);
		//������username��װ������user��modifideUser��
		user.setUsername(username);
		//����ʱ�䣬��װ������user��modifideTime��
		user.setModifideTime(new Date());
		//ִ���޸� ��userMapper.updateInfo��user��
		Integer rows = userMapper.updateInfo(user);
		//�жϷ�����Ӱ��������Ƿ�Ϊ1
		if(rows !=1) {
		//�ǣ����´����׳�UpdateException
			throw new UpdateException("�������ݳ���δ֪����");
		}	
	}
	

	//��ѯ�û����ݱ����޸�����
	@Override
	public User getByUid(Integer uid) {
		// ͨ������uid��ѯ�û�����
		User result = userMapper.findByUid(uid);
		// �жϲ�ѯ����Ƿ�Ϊnull
		if (result == null) {
			// �ǣ��û����ݲ����ڣ��׳�UsernotFoundException
			throw new UserNotFoundException("�û�������");
		}
		// �жϲ�ѯ����е�isDelete�Ƿ�Ϊ1
		if (result.getIsDelete().equals(1)) {
			// �ǣ��û����ݱ����Ϊɾ�����׳�UsernotFoundException
			throw new UserNotFoundException("�û�������ɾ��");
		}
		// �����µ�User����
		User user = new User();
		// ����ѯ����е�username,gender,phone,email��װ���¶�����
		user.setUsername(result.getUsername());
		user.setGender(result.getGender());
		user.setPhone(result.getPhone());
		user.setEmail(result.getEmail());
		// �����¶���
		return user;
	}
	
	
	

	/**
	 * ִ���������
	 * 
	 * @param password
	 *            ԭ����
	 * @param salt
	 *            ��ֵ
	 * @return ���ܺ������
	 */
	private String getMd5Password(String password, String salt) {
		// ���ܹ�����ԭ����������Ҳ��ƴ����ֵ��ѭ������5��
		String str = salt + password + salt;
		for (int i = 0; i < 5; i++) {
			str = DigestUtils.md5DigestAsHex(str.getBytes());
		}
		return str;
	}

}
