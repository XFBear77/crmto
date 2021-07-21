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
 * 处理购物车数据的业务层实现类
 */
@Service
public class CartServiceImpl implements ICartService {
	@Autowired
	private CartMapper cartMapper;
	
	@Autowired
	private IProductService productService;
	@Override
	public void addToCat(Integer uid, Integer pid, Integer num, String username) {
		//根据参数uid和pid查询数据
		Cart result = findUidAndPid(uid, pid);
		Date now = new Date();
		//判断查询结果是否为null	
		if(result==null) {
		//--是
		//通过 IProductService 查出商品价格
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
			//--执行添加
			save(cart);
		
		} else {
			//否
			//从查询结果中获取原数量，和参数num相加，得到新数量
			Integer number = result.getNum()+num;
			//--执行更新数量
			updateNum(result.getCid(), number, username, now);
		}
		
		
	}

	/**
	 * 向购物车表中插入数据
	 * @param cart 购物车实体类
	 */
	private void save(Cart cart) {
		Integer rows = cartMapper.save(cart);
		if(rows !=1) {
			throw new InsertException("插入数据异常！");
		}
	}
	
	@Override
	public Integer addNum(Integer cid, Integer uid, String username) {
		//根据参数cid查询购物车数据
		Cart result = findByCid(cid);
		//判断查询结果是否为null： CartNotFoundException
		if(result ==null) {
			throw new CartNotFoundException("购物车数据不存在异常");
		}
		//判断查询结果中的uid与参数uid是否不一致：AccessDenideException
		if(!result.getUid().equals(uid)) {
			throw new AccessDenidException("非法访问");
		}
		//从查询结果取出num，并加1
		Integer number = result.getNum() + 1;
		//执行更新数量
		updateNum(cid, number, username,new Date());
		//返回新的数量
		return number;
	}
	
	/**
	 * 查询某用户的购物车列表
	 */
	@Override
	public List<CartVO> getByUid(Integer uid) {
		
		return findByUid(uid);
	}
	
	/**
	 * 根据若干个购物车数据查询数据
	 */
	@Override
	public List<CartVO> getByCids(Integer[] cids,Integer uid) {
		//执行查询
		List<CartVO> list = findByCids(cids);
		//遍历查询结果，移除非当前uid的数据
		//迭代器是一种安全的遍历集合的做法
		//循环过程中长度会发生变化的时候，要使用用迭代器
		Iterator<CartVO> it = list.iterator();
		while(it.hasNext()) {
			CartVO item = it.next();
			if(!item.getUid().equals(uid)) {
				System.err.println("找到归属的错误的数据，将移除"+item);
				it.remove();
			}
		}
		//返回
		return list;
	}
	
	/**
	 * 查询某用户的购物车列表
	 * @param uid 用户的id
	 * @return 返回用户购物车列表
	 */
	private List<CartVO> findByUid(Integer uid){
		return cartMapper.findByUid(uid);
	}
	
	/**
	 * 修改购物车表中商品的数量
	 * @param cid 购物车数据id
	 * @param num 新的数量
	 * @param modifideUser 修改执行人
	 * @param modifideTime 修改时间
	 */
	private void updateNum(Integer cid,Integer num,
			String modifideUser,Date modifideTime) {
		 Integer rows = cartMapper.updateNum(cid, num, modifideUser, modifideTime);
		 if(rows !=1) {
			 throw new UpdateException("购物车数据时出现错误");
		 }
	}
	
	/**
	 * 根据购物车数据id查询购物车详情
	 * @param cid 购物车数据id
	 * @return  返回匹配的购物车详情，如果没有匹配数据，返回null
	 */
	private Cart findByCid(Integer cid) {
		return cartMapper.findByCid(cid);
	}
	
	/**
	 * 获取某用户在购物车中添加的某商品的数据
	 * @param uid 用户id
	 * @param pid 商品id
	 * @return 相关的购物车数据，如果没有匹配数据，返回null
	 */
	private Cart findUidAndPid(Integer uid ,Integer pid) {
		return cartMapper.findUidAndPid(uid, pid);
	}

	

	
	/**
	 * 根据若干个购物车数据查询数据
	 * @param cids 若干个购物车数据id
	 * @return 匹配的购物车数据的列表
	 */
	private List<CartVO> findByCids(Integer[] cids){
		
		return cartMapper.findByCids(cids);
	}
	
	
	

}
