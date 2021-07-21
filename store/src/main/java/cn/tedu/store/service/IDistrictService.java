package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.District;
/**
 * ����ʡ/��/�����ݵ�ҵ���ӿ�
 */
public interface IDistrictService {
	/**
	 * ���ݸ������������Ż�ȡȫ�����е�ʡ/ĳʡ������/ĳ�����е������б�
	 * @param parent �������������ţ������Ҫ��ȡȫ�����е�ʡ����ʹ��86
	 * @return ����ȫ�����е�ʡ/ĳʡ������/ĳ�����е������б�
	 */
	List<District> getByParent(String parent);
	
	/**
	 * ����ʡ/��/�����������Ż�ȡ����
	 * @param codeʡ/��/������������
	 * @return ƥ���ʡ/��/�������飬���û��ƥ������ݷ���null
	 */
	District getByCode(String code);
}
