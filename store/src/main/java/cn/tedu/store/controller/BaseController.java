package cn.tedu.store.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ExceptionHandler;

import cn.tedu.store.controller.ex.FileSizeException;
import cn.tedu.store.controller.ex.FileTypeException;
import cn.tedu.store.controller.ex.FileUploadException;
import cn.tedu.store.controller.ex.FileUploadIOException;
import cn.tedu.store.controller.ex.FileUploadStateException;
import cn.tedu.store.service.ex.AccessDenidException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.AddressSizeLimitException;
import cn.tedu.store.service.ex.DeleteException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.ProductNotFoundException;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameConflictException;
import cn.tedu.store.util.JsonResult;

/**
 * ��������Ļ���
 */
public class BaseController {
	/**
	 * ��Ӧ�ɹ��ı�ʶ��
	 */
	public static final int SUCCESS = 2000;
	
	/**
	 * ��Session�л�ȡ�û�id
	 * @param session Session����
	 * @return ��ǰ��¼���û�id
	 */
	protected Integer getUidFromSession(HttpSession session) {
		return Integer.valueOf(session.getAttribute("uid").toString());
	}
	
	/**
	 * ��Session�л�ȡ��ǰ��¼���û���
	 * @param session Session����
	 * @return ��ǰ��¼���û���
	 */
	protected String getUsernameFromSession(HttpSession session) {
		return session.getAttribute("username").toString();
	}

	@ExceptionHandler({ServiceException.class,FileUploadException.class})
	public JsonResult<Void> handleException(Throwable e) {
		JsonResult<Void> jr = new JsonResult<>(e);

		if (e instanceof UsernameConflictException) {
			// 4000-�û�����ͻ�쳣������:ע��ʱ�û����ѱ�ռ��
			jr.setState(4000);
		} else if (e instanceof UserNotFoundException) {
			// 4001-�û����ݲ�����
			jr.setState(4001);
		} else if (e instanceof PasswordNotMatchException) {
			// 4002-��֤����ʧ��
			jr.setState(4002);
		} else if(e instanceof AddressSizeLimitException) {
			//4003-�ջ���ַ�������ﵽ����
			jr.setState(4003);
		}else if(e instanceof AddressNotFoundException) {
			//4004-�ջ���ַ���ݲ�����
			jr.setState(4004);
		} else if(e instanceof AccessDenidException ) {
			//4005-�ܾ����ʣ����磺��ѯ���ݹ�������
			jr.setState(4005);
		} else if(e instanceof ProductNotFoundException ) {
			//4006-��Ʒ���ݲ�����
			jr.setState(4006);
		} else if (e instanceof InsertException) {
			// 5000-���������쳣
			jr.setState(5000);
		} else if(e instanceof UpdateException) {
			//5001-���������쳣
			jr.setState(5001);
		}  else if(e instanceof DeleteException) {
			//5002-ɾ�������쳣
			jr.setState(5002);
		} else if(e instanceof FileUploadException) {
			//6000-�ϴ��ļ�Ϊ��,����,û��ѡ���ļ�����ѡ���ļ���0�ֽڵ�
			jr.setState(6000);
		} else if(e instanceof FileSizeException) {
			//6001-�ϴ��ļ���С��������
			jr.setState(6001);
		} else if(e instanceof FileTypeException) {
			//6002-�ϴ��ļ����ͳ�������
			jr.setState(6002);
		}  else if(e instanceof FileUploadStateException) {
			//6003-�ϴ��ļ����ֶ�д����
			jr.setState(6003);
		}  else if(e instanceof FileUploadIOException) {
			//6004-�ϴ��ļ�״̬�쳣�������ļ��Ѿ����ƶ���ɾ�����޷��������ʸ��ļ�
			jr.setState(6004);
		} 

		return jr;
	}
}
