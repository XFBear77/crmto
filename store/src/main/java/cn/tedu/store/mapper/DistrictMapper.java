package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.District;

/**
 *����ʡ/��/�����ݵĳ־ò�ӿ�
 */
public interface DistrictMapper {
	/**
	 * ���ݸ������������Ż�ȡȫ�����е�ʡ/ĳʡ������/ĳ�����е������б�
	 * @param parent �������������ţ������Ҫ��ȡȫ�����е�ʡ����ʹ��86
	 * @return ����ȫ�����е�ʡ/ĳʡ������/ĳ�����е������б�
	 */
	List<District> findByParent(String parent);
	
	/**
	 * ����ʡ/��/�����������Ż�ȡ����
	 * @param codeʡ/��/������������
	 * @return ƥ���ʡ/��/�������飬���û��ƥ������ݷ���null
	 */
	District findByCode(String code);
}
