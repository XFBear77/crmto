package cn.tedu.store.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.tedu.store.controller.ex.FileEmptyException;
import cn.tedu.store.controller.ex.FileSizeException;
import cn.tedu.store.controller.ex.FileTypeException;
import cn.tedu.store.controller.ex.FileUploadIOException;
import cn.tedu.store.controller.ex.FileUploadStateException;
import cn.tedu.store.entity.User;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.util.JsonResult;

/**
 * �����û������������Ŀ�����
 */
@RestController
@RequestMapping("users")
public class UserController extends BaseController {

	@Autowired
	private IUserService userService;

	@PostMapping("reg")
	public JsonResult<Void> reg(User user) {
		userService.reg(user);
		return new JsonResult<>(SUCCESS);// ����
	}

	@RequestMapping("login")
	public JsonResult<User> login(String username, String password, HttpSession session) {
		User data = userService.login(username, password);
		session.setAttribute("uid", data.getUid());
		session.setAttribute("username", data.getUsername());
		return new JsonResult<>(SUCCESS, data);
	}

	@RequestMapping("change_password")
	public JsonResult<Void> changePassword(@RequestParam("old_password") String oldPassword,
			@RequestParam("new_password") String newPassword, HttpSession session) {
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		userService.changePassword(uid, username, oldPassword, newPassword);
		return new JsonResult<>(SUCCESS);
	}

	/**
	 * �����ϴ����ļ���С
	 */
	private static final long FILE_MAX_SIZE = 700 * 1024;

	/**
	 * �����ϴ����ļ�������
	 */
	private static final List<String> FILE_TYPES = new ArrayList<>();

	static {
		FILE_TYPES.add("image/jpeg");
		FILE_TYPES.add("image/png");
	}

	// ����SpringMVC���ϴ�
	@PostMapping("change_avatar")
	public JsonResult<String> changeAvatar(@RequestParam("file") MultipartFile file, HttpSession session) {
		// ��飺�ϴ��ļ��Ƿ�Ϊ��
		if (file.isEmpty()) {
			// �ǣ�û��ѡ���ļ�����ѡ����ļ���0�ֽ�
			throw new FileEmptyException("û��ѡ���ļ�,��ѡ���ļ���0�ֽڵ�");
		}
		// ��飺�ϴ����ļ���С
		if (file.getSize() > FILE_MAX_SIZE) {
			throw new FileSizeException("�������ϴ�����" + (FILE_MAX_SIZE / 1024) + "KB���ļ���");
		}

		// ��飺�ϴ����ļ�����
		if (!FILE_TYPES.contains(file.getContentType())) {
			throw new FileTypeException("��֧��" + FILE_TYPES + "���͵��ļ���");
		}

		// �ϴ����ļ���
		String dirPath = session.getServletContext().getRealPath("upload");
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// ��ȡԭʼ�ļ���
		String originalFilename = file.getOriginalFilename();
		// ��չ��
		String suffix = "";
		int index = originalFilename.lastIndexOf(".");
		if (index > 0) {
			suffix = originalFilename.substring(index);
		}
		// �ļ�ȫ��
		String filename = UUID.randomUUID().toString() + suffix;
		// ���浽���ļ�
		File dest = new File(dir, filename);
		// �����û��ϴ���ͷ��
		try {
			file.transferTo(dest);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			throw new FileUploadStateException("�ļ������Ѿ����ƶ���ɾ�������ɷ��ʵ����ļ���");
		} catch (IOException e) {
			e.printStackTrace();
			throw new FileUploadIOException("���ֶ�д����");
		}

		// ͷ��·��
		String avatar = "/upload/" + filename;
		// ��Session�л�ȡuid��username
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		// ִ�и������ݿ�
		userService.chageAvatar(uid, username, avatar);
		return new JsonResult<>(SUCCESS, avatar);
	}

	@PostMapping("change_info")
	public JsonResult<Void> changeInfo(User user, HttpSession session) {
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		userService.changeInfo(uid, username, user);
		return new JsonResult<>(SUCCESS);
	}

	@GetMapping("get_by_uid")
	public JsonResult<User> getByUid(HttpSession session) {
		Integer uid = getUidFromSession(session);
		User data = userService.getByUid(uid);
		return new JsonResult<>(SUCCESS, data);
	}

}
