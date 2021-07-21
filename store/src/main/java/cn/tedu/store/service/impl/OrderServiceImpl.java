package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;
import cn.tedu.store.mapper.OrderMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.service.IOrderService;
import cn.tedu.store.service.ex.AccessDenidException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.vo.CartVO;
/**
 * ���������ݺͶ�����Ʒ���ݵ�ҵ���ʵ����
 */
@Service
public class OrderServiceImpl  implements IOrderService{
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private IAddressService addressService;
	
	@Autowired
	private ICartService cartService;
	
	@Override
	//2�����ϵ���ɾ�� һ��Ҫ������ע��
	@Transactional
	public Order createOrder(Integer aid, Integer[] cids, Integer uid, String username) {
		//ͨ������aid��ѯ�ջ���ַ����:ͨ��IAddressService
		Address address = addressService.getByAid(aid);
		System.err.println(address);
		//������ݹ����Ƿ���ȷ
		if(!address.getUid().equals(uid)) {
			throw new AccessDenidException("�Ƿ������ջ���ַ���ݣ�");
		}
		
		//ͨ������cids��ѯ���ﳵ���ݣ��õ�List<CartVo>
		List<CartVO> carts = cartService.getByCids(cids, uid);
		//����List<CartVo>,������Ʒ�ܼ�
		Long totalPrice = 0L;
		for (CartVO cart : carts) {
			totalPrice +=cart.getRealPrice()*cart.getNum();
		}	
		//������ǰʱ�����now
		Date now  = new Date();
		//����Order����
		Order order = new Order();
		//��װOrder���ԣ�uid 
		order.setUid(uid);
		//��װOrder���ԣ�recv_???�������
		order.setRecvName(username);
		order.setRecvPhone(address.getPhone());
		order.setRecvProvince(address.getProvinceName());
		order.setRecvCity(address.getCityName());
		order.setRecvArea(address.getAreaName());
		order.setRecvAddress(address.getAreaName());
		//��װOrder���ԣ�total_price(���ϼ������Ʒ�ܼ�)
		order.setTotalPrice(totalPrice);
		//��װOrder���ԣ�status(0)
		order.setStatus(0);
		//��װOrder���ԣ�order_time(now)
		order.setOrderTime(now);
		//��װOrder���ԣ�pay_type(null),pay_time(null)
		order.setPayType(null);
		order.setPayTime(null);
		//��װOrder���ԣ�4����־
		order.setCreatedUser(username);
		order.setCreatedTime(now);
		order.setModifideUser(username);
		order.setModifideTime(now);
		//��t_order���в������ݣ�saveOrder(Order order)
		saveOrder(order);
		
		//����List<CartVo>
		for (CartVO cart : carts) {
			//--����OrderItem����
			OrderItem item = new OrderItem();
			//--��װOrderItem����:oid(order.getOid())
			item.setOid(order.getOid());
			//--��װOrderItem���ԣ�pid,title��image��price��num
			item.setPid(cart.getPid());
			item.setTitle(cart.getTitle());
			item.setImage(cart.getImage());
			item.setPrice(cart.getRealPrice());
			item.setNum(cart.getNum());
			//--��װOrderItem����:4����־
			item.setCreatedUser(username);
			item.setCreatedTime(now);
			item.setModifideUser(username);
			item.setModifideTime(now);
			//��t_order_item���в�������������
			saveOredrItem(item);
		}

		//����order���󣬷���֮ǰ���Խ�������������Ϊnull
		order.setCreatedUser(null);
		order.setCreatedTime(null);
		order.setModifideUser(null);
		order.setModifideTime(null);
		
		return order;
	}
	
	
	/**
	 * ���붩������
	 * @param order ��������
	 * @return ������Ӱ�������
	 */
	private void saveOrder(Order order) {
		Integer rows = orderMapper.saveOrder(order);
		if(rows !=1) {
			throw new InsertException("���붩���������ӳ��ִ���!");
		}
	}
	
	/**
	 * ���붩����Ʒ����
	 * @param orderItem ������Ʒ
	 * @return ������Ӱ�������
	 */
	private void saveOredrItem(OrderItem orderItem) {
		Integer rows = orderMapper.saveOredrItem(orderItem);
		if(rows !=1) {
			throw new InsertException("���붩����Ʒ���ִ���");
		}
	}
}
