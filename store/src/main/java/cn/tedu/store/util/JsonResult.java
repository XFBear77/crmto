package cn.tedu.store.util;

import java.io.Serializable;

/**
 * ��װ��Ӧ��JSON�������
 */
public class JsonResult<T> implements Serializable {

	private static final long serialVersionUID = -4188714538178128702L;
	private Integer state;
	private String message;
	private T data;

	public JsonResult(Integer state) {
		super();
		this.state = state;
	}

	public JsonResult() {
		super();
	}

	public JsonResult(Throwable e) {
		super();
		this.message = e.getMessage();
	}

	public JsonResult(Integer state, T data) {
		super();
		this.state = state;
		this.data = data;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "JsonResult [state=" + state + ", message=" + message + ", data=" + data + "]";
	}

}
