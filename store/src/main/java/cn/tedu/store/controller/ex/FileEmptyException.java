package cn.tedu.store.controller.ex;

import cn.tedu.store.controller.ex.FileUploadException;

/**
 * 上传文件为空,例如,没有选择文件，或选择文件是0字节的
 */
public class FileEmptyException extends FileUploadException {

	private static final long serialVersionUID = -5877710337474397491L;

	public FileEmptyException() {
		super();
	}

	public FileEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileEmptyException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileEmptyException(String message) {
		super(message);
	}

	public FileEmptyException(Throwable cause) {
		super(cause);
	}
	
	
}
