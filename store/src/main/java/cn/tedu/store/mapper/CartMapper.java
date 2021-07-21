package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.vo.CartVO;
/**
 * �����ﳵ�ĳ־ò�ӿ�
 */
public interface CartMapper {
	
	/** ���ﳵ���в�������
	 * ���ﳵ���в�������
	 * @param cart ���ﳵʵ����
	 * @return ������Ӱ�������
	 */
	Integer save(Cart cart);
	
	/**
	 * �޸Ĺ��ﳵ������Ʒ������
	 * @param cid ���ﳵ����id
	 * @param num �µ�����
	 * @param modifideUser �޸�ִ����
	 * @param modifideTime �޸�ʱ��
	 * @return ������Ӱ�������
	 */
	Integer updateNum(@Param("cid")Integer cid,@Param("num")Integer num,
			@Param("modifideUser")String modifideUser,@Param("modifideTime")Date modifideTime);
	
	/**
	 * ���ݹ��ﳵ����id��ѯ���ﳵ����
	 * @param cid ���ﳵ����id
	 * @return  ����ƥ��Ĺ��ﳵ���飬���û��ƥ�����ݣ�����null
	 */
	Cart findByCid(Integer cid);
	
	/**
	 * ��ȡĳ�û��ڹ��ﳵ����ӵ�ĳ��Ʒ������
	 * @param uid �û�id
	 * @param pid ��Ʒid
	 * @return ��صĹ��ﳵ���ݣ����û��ƥ�����ݣ�����null
	 */
	Cart findUidAndPid(@Param("uid")Integer uid,@Param("pid")Integer pid);
	
	/**
	 * ��ѯĳ�û��Ĺ��ﳵ�б�
	 * @param uid �û���id
	 * @return �����û����ﳵ�б�
	 */
	List<CartVO> findByUid(Integer uid);
	
	/**
	 * �������ɸ����ﳵ���ݲ�ѯ����
	 * @param cids ���ɸ����ﳵ����id
	 * @return ƥ��Ĺ��ﳵ���ݵ��б�
	 */
	List<CartVO> findByCids(Integer[] cids);
	
	
}
