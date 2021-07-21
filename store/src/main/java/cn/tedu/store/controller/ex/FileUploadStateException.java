package cn.tedu.store.controller.ex;
/**
 * 上传文件出现读写错误
 */
public class FileUploadStateException extends FileUploadException {

	private static final long serialVersionUID = 4286991940161762027L;

	public FileUploadStateException() {
		super();
	}

	public FileUploadStateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileUploadStateException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileUploadStateException(String message) {
		super(message);
	}

	public FileUploadStateException(Throwable cause) {
		super(cause);
	}
	
	
}
