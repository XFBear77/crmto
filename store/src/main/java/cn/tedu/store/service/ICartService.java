package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.vo.CartVO;

/**
 *�����ﳵ���ݵ�ҵ���ӿ� 
 */
public interface ICartService {
	
	/**
	 * ����Ʒ��ӵ����ﳵ
	 * @param uid ��ǰ��¼���û���id
	 * @param pid ��Ʒ��id
	 * @param num ��ӵ����ﳵ�е�����
	 * @param username ����ִ����
	 */
	void addToCat(Integer uid,Integer pid,Integer num,String username);
	
	/**
	 * ��ѯĳ�û��Ĺ��ﳵ�б�
	 * @param uid �û���id
	 * @return �����û����ﳵ�б�
	 */
	List<CartVO> getByUid(Integer uid);
	
	/**
	 * �����ﳵ��ָ�����ݵ���Ʒ��������1
	 * @param cid  ���ﳵ���ݵ�id
	 * @param uid  ��ǰ�û���id
	 * @param username ��ǰ��¼���û���
	 * @return �µ�����
	 */
	Integer addNum(Integer cid ,Integer uid,String username);
	
	
	/**
	 * �������ɸ����ﳵ���ݲ�ѯ����
	 * @param cids ���ɸ����ﳵ����id
	 * @param  uid ��ǰ��¼���û�id
	 * @return ƥ��Ĺ��ﳵ���ݵ��б�
	 */
	List<CartVO> getByCids(Integer[] cids,Integer uid);
}
