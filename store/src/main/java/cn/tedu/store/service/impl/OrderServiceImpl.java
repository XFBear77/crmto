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
 * 处理订单数据和订单商品数据的业务层实现类
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
	//2次以上的增删改 一定要加事务注解
	@Transactional
	public Order createOrder(Integer aid, Integer[] cids, Integer uid, String username) {
		//通过参数aid查询收货地址数据:通过IAddressService
		Address address = addressService.getByAid(aid);
		System.err.println(address);
		//检查数据归属是否正确
		if(!address.getUid().equals(uid)) {
			throw new AccessDenidException("非法访问收货地址数据！");
		}
		
		//通过参数cids查询购物车数据，得到List<CartVo>
		List<CartVO> carts = cartService.getByCids(cids, uid);
		//遍历List<CartVo>,计算商品总价
		Long totalPrice = 0L;
		for (CartVO cart : carts) {
			totalPrice +=cart.getRealPrice()*cart.getNum();
		}	
		//创建当前时间对象now
		Date now  = new Date();
		//创建Order对象
		Order order = new Order();
		//封装Order属性：uid 
		order.setUid(uid);
		//封装Order属性：recv_???相关数据
		order.setRecvName(username);
		order.setRecvPhone(address.getPhone());
		order.setRecvProvince(address.getProvinceName());
		order.setRecvCity(address.getCityName());
		order.setRecvArea(address.getAreaName());
		order.setRecvAddress(address.getAreaName());
		//封装Order属性：total_price(以上计算的商品总价)
		order.setTotalPrice(totalPrice);
		//封装Order属性：status(0)
		order.setStatus(0);
		//封装Order属性：order_time(now)
		order.setOrderTime(now);
		//封装Order属性：pay_type(null),pay_time(null)
		order.setPayType(null);
		order.setPayTime(null);
		//封装Order属性：4项日志
		order.setCreatedUser(username);
		order.setCreatedTime(now);
		order.setModifideUser(username);
		order.setModifideTime(now);
		//向t_order表中插入数据：saveOrder(Order order)
		saveOrder(order);
		
		//遍历List<CartVo>
		for (CartVO cart : carts) {
			//--创建OrderItem对象
			OrderItem item = new OrderItem();
			//--封装OrderItem属性:oid(order.getOid())
			item.setOid(order.getOid());
			//--封装OrderItem属性：pid,title，image，price，num
			item.setPid(cart.getPid());
			item.setTitle(cart.getTitle());
			item.setImage(cart.getImage());
			item.setPrice(cart.getRealPrice());
			item.setNum(cart.getNum());
			//--封装OrderItem属性:4项日志
			item.setCreatedUser(username);
			item.setCreatedTime(now);
			item.setModifideUser(username);
			item.setModifideTime(now);
			//向t_order_item表中插入若干条数据
			saveOredrItem(item);
		}

		//返回order对象，返回之前可以将部分数据设置为null
		order.setCreatedUser(null);
		order.setCreatedTime(null);
		order.setModifideUser(null);
		order.setModifideTime(null);
		
		return order;
	}
	
	
	/**
	 * 插入订单数据
	 * @param order 订单数据
	 * @return 返回受影响的行数
	 */
	private void saveOrder(Order order) {
		Integer rows = orderMapper.saveOrder(order);
		if(rows !=1) {
			throw new InsertException("插入订单数据增加出现错误!");
		}
	}
	
	/**
	 * 插入订单商品数据
	 * @param orderItem 订单商品
	 * @return 返回受影响的行数
	 */
	private void saveOredrItem(OrderItem orderItem) {
		Integer rows = orderMapper.saveOredrItem(orderItem);
		if(rows !=1) {
			throw new InsertException("插入订单商品出现错误！");
		}
	}
}
