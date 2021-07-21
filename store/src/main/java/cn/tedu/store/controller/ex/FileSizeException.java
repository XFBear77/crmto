package cn.tedu.store.controller.ex;

import cn.tedu.store.service.ex.UpdateException;
/**
 *上传文件大小超出限制
 */
public class FileSizeException extends UpdateException {

	private static final long serialVersionUID = 5302269116229851354L;

	public FileSizeException() {
		super();
	}

	public FileSizeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileSizeException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileSizeException(String message) {
		super(message);
	}

	public FileSizeException(Throwable cause) {
		super(cause);
	}

}
