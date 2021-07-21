package cn.tedu.store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.service.ICartService;
import cn.tedu.store.util.JsonResult;
import cn.tedu.store.vo.CartVO;

@RestController
@RequestMapping("carts")
public class CartController extends BaseController {

	@Autowired
	private ICartService cartService;

	@RequestMapping("add")
	public JsonResult<Void> addToCart(Integer pid, Integer num, HttpSession session) {
		// ��session�л�ȡuid��username
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		// ����ҵ������ִ������
		cartService.addToCat(uid, pid, num, username);
		// ��Ӧ�ɹ�
		return new JsonResult<>(SUCCESS);
	}

	@GetMapping("/")
	public JsonResult<List<CartVO>> getByUid(HttpSession session) {
		// ��session�л�ȡuid
		Integer uid = getUidFromSession(session);
		// ִ�в�ѯ�������ȡ���
		List<CartVO> data = cartService.getByUid(uid);
		// ��Ӧ�ɹ�������ѯ���
		return new JsonResult<>(SUCCESS, data);
	}

	@RequestMapping("{cid}/add_num")
	public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid, HttpSession session) {
		// ��session�л�ȡuid��username
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		// ����ҵ�����ִ�����ӣ�����ȡ���ؽ��
		Integer data = cartService.addNum(cid, uid, username);
		// ���سɹ������������
		return new JsonResult<>(SUCCESS, data);
	}

	@GetMapping("get_by_cids")
	public JsonResult<List<CartVO>> getByCid(Integer[] cids,HttpSession session) {
		//��session�л�ȡuid
		Integer uid = getUidFromSession(session);
		// ����ҵ�������ѯ����
		List<CartVO> data = cartService.getByCids(cids,uid);
		// ��Ӧ�ɹ�������ѯ���
		return new JsonResult<>(SUCCESS, data);
	}
}
