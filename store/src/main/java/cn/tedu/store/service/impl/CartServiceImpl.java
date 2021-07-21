package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.mapper.CartMapper;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.service.IProductService;
import cn.tedu.store.service.ex.AccessDenidException;
import cn.tedu.store.service.ex.CartNotFoundException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.vo.CartVO;

/**
 * �����ﳵ���ݵ�ҵ���ʵ����
 */
@Service
public class CartServiceImpl implements ICartService {
	@Autowired
	private CartMapper cartMapper;
	
	@Autowired
	private IProductService productService;
	@Override
	public void addToCat(Integer uid, Integer pid, Integer num, String username) {
		//���ݲ���uid��pid��ѯ����
		Cart result = findUidAndPid(uid, pid);
		Date now = new Date();
		//�жϲ�ѯ����Ƿ�Ϊnull	
		if(result==null) {
		//--��
		//ͨ�� IProductService �����Ʒ�۸�
			Long price = productService.getById(pid).getPrice();
			Cart cart = new Cart();
			cart.setUid(uid);
			cart.setPid(pid);
			cart.setNum(num);
			cart.setPrice(price);
			cart.setCreatedUser(username);
			cart.setModifideUser(username);
			cart.setCreatedTime(now);
			cart.setModifideTime(now);
			//--ִ�����
			save(cart);
		
		} else {
			//��
			//�Ӳ�ѯ����л�ȡԭ�������Ͳ���num��ӣ��õ�������
			Integer number = result.getNum()+num;
			//--ִ�и�������
			updateNum(result.getCid(), number, username, now);
		}
		
		
	}

	/**
	 * ���ﳵ���в�������
	 * @param cart ���ﳵʵ����
	 */
	private void save(Cart cart) {
		Integer rows = cartMapper.save(cart);
		if(rows !=1) {
			throw new InsertException("���������쳣��");
		}
	}
	
	@Override
	public Integer addNum(Integer cid, Integer uid, String username) {
		//���ݲ���cid��ѯ���ﳵ����
		Cart result = findByCid(cid);
		//�жϲ�ѯ����Ƿ�Ϊnull�� CartNotFoundException
		if(result ==null) {
			throw new CartNotFoundException("���ﳵ���ݲ������쳣");
		}
		//�жϲ�ѯ����е�uid�����uid�Ƿ�һ�£�AccessDenideException
		if(!result.getUid().equals(uid)) {
			throw new AccessDenidException("�Ƿ�����");
		}
		//�Ӳ�ѯ���ȡ��num������1
		Integer number = result.getNum() + 1;
		//ִ�и�������
		updateNum(cid, number, username,new Date());
		//�����µ�����
		return number;
	}
	
	/**
	 * ��ѯĳ�û��Ĺ��ﳵ�б�
	 */
	@Override
	public List<CartVO> getByUid(Integer uid) {
		
		return findByUid(uid);
	}
	
	/**
	 * �������ɸ����ﳵ���ݲ�ѯ����
	 */
	@Override
	public List<CartVO> getByCids(Integer[] cids,Integer uid) {
		//ִ�в�ѯ
		List<CartVO> list = findByCids(cids);
		//������ѯ������Ƴ��ǵ�ǰuid������
		//��������һ�ְ�ȫ�ı������ϵ�����
		//ѭ�������г��Ȼᷢ���仯��ʱ��Ҫʹ���õ�����
		Iterator<CartVO> it = list.iterator();
		while(it.hasNext()) {
			CartVO item = it.next();
			if(!item.getUid().equals(uid)) {
				System.err.println("�ҵ������Ĵ�������ݣ����Ƴ�"+item);
				it.remove();
			}
		}
		//����
		return list;
	}
	
	/**
	 * ��ѯĳ�û��Ĺ��ﳵ�б�
	 * @param uid �û���id
	 * @return �����û����ﳵ�б�
	 */
	private List<CartVO> findByUid(Integer uid){
		return cartMapper.findByUid(uid);
	}
	
	/**
	 * �޸Ĺ��ﳵ������Ʒ������
	 * @param cid ���ﳵ����id
	 * @param num �µ�����
	 * @param modifideUser �޸�ִ����
	 * @param modifideTime �޸�ʱ��
	 */
	private void updateNum(Integer cid,Integer num,
			String modifideUser,Date modifideTime) {
		 Integer rows = cartMapper.updateNum(cid, num, modifideUser, modifideTime);
		 if(rows !=1) {
			 throw new UpdateException("���ﳵ����ʱ���ִ���");
		 }
	}
	
	/**
	 * ���ݹ��ﳵ����id��ѯ���ﳵ����
	 * @param cid ���ﳵ����id
	 * @return  ����ƥ��Ĺ��ﳵ���飬���û��ƥ�����ݣ�����null
	 */
	private Cart findByCid(Integer cid) {
		return cartMapper.findByCid(cid);
	}
	
	/**
	 * ��ȡĳ�û��ڹ��ﳵ����ӵ�ĳ��Ʒ������
	 * @param uid �û�id
	 * @param pid ��Ʒid
	 * @return ��صĹ��ﳵ���ݣ����û��ƥ�����ݣ�����null
	 */
	private Cart findUidAndPid(Integer uid ,Integer pid) {
		return cartMapper.findUidAndPid(uid, pid);
	}

	

	
	/**
	 * �������ɸ����ﳵ���ݲ�ѯ����
	 * @param cids ���ɸ����ﳵ����id
	 * @return ƥ��Ĺ��ﳵ���ݵ��б�
	 */
	private List<CartVO> findByCids(Integer[] cids){
		
		return cartMapper.findByCids(cids);
	}
	
	
	

}
