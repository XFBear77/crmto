package cn.tedu.store.service.ex;
/**
 * �ܾ����ʣ����磺��ѯ���ݹ�������
 */
public class AccessDenidException extends ServiceException{
	
	private static final long serialVersionUID = -7312613135073637047L;

	public AccessDenidException() {
		super();
	}

	public AccessDenidException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AccessDenidException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccessDenidException(String message) {
		super(message);
	}

	public AccessDenidException(Throwable cause) {
		super(cause);
	}
	
	
}
