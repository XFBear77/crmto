package cn.tedu.store.controller.ex;

/**
 * 上传文件状态异常，可能文件已经被移动或删除，无法继续访问该文件
 */
public class FileUploadIOException extends FileUploadException {

	private static final long serialVersionUID = -5189827914052025616L;

	public FileUploadIOException() {
		super();
	}

	public FileUploadIOException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileUploadIOException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileUploadIOException(String message) {
		super(message);
	}

	public FileUploadIOException(Throwable cause) {
		super(cause);
	}
	
	
}
