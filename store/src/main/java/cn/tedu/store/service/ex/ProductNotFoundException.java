package cn.tedu.store.service.ex;

/**
 *��Ʒ���ݲ�����
 */
public class ProductNotFoundException extends ServiceException{

	private static final long serialVersionUID = 6109047540146573846L;

	public ProductNotFoundException() {
		super();
	}

	public ProductNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ProductNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductNotFoundException(String message) {
		super(message);
	}

	public ProductNotFoundException(Throwable cause) {
		super(cause);
	}
	
	
}
