package cn.tedu.store.service.ex;
/**
 *用户名冲突异常，例如:注册时用户名已被占用
 */
public class UsernameConflictException extends ServiceException {

	private static final long serialVersionUID = -935760340922600036L;

	public UsernameConflictException() {
		super();
	}

	public UsernameConflictException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UsernameConflictException(String message, Throwable cause) {
		super(message, cause);
	}

	public UsernameConflictException(String message) {
		super(message);
	}

	public UsernameConflictException(Throwable cause) {
		super(cause);
	}
	
	
}
