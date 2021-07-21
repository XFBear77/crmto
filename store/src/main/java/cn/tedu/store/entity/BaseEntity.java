package cn.tedu.store.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类的基类(某些特定类型它们最基础的父类)
 * 默认权限同包可以用，子类用不了
 */
abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 8631100451835159197L;

	private String createdUser;
	private Date createdTime;
	private String modifideUser;
	private Date modifideTime;

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getModifideUser() {
		return modifideUser;
	}

	public void setModifideUser(String modifideUser) {
		this.modifideUser = modifideUser;
	}

	public Date getModifideTime() {
		return modifideTime;
	}

	public void setModifideTime(Date modifideTime) {
		this.modifideTime = modifideTime;
	}

	@Override
	public String toString() {
		return "BaseEntity [createdUser=" + createdUser + ", createdTime=" + createdTime + ", modifideUser="
				+ modifideUser + ", modifideTime=" + modifideTime + "]";
	}

}
