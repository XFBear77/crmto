package cn.tedu.store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.util.JsonResult;

/**
 *
 */
@RestController
@RequestMapping("addresses")
public class AddressController extends BaseController {

	@Autowired
	private IAddressService iaddressService;

	@RequestMapping("addnew")
	public JsonResult<Void> addnew(Address address, HttpSession session) {
		// ��session�л�ȡuid��username
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		// ִ������
		iaddressService.addnew(address, uid, username);
		// ��Ӧ�ɹ�
		return new JsonResult<>(SUCCESS);
	}

	@GetMapping("/")
	public JsonResult<List<Address>> getByUid(HttpSession session) {
		Integer uid = getUidFromSession(session);
		List<Address> data = iaddressService.getByUid(uid);
		return new JsonResult<>(SUCCESS, data);
	}
	
	@RequestMapping("{aid}/set_default")
	public JsonResult<Void> setDefault(@PathVariable("aid")Integer aid,HttpSession session){
		// ��session�л�ȡuid��username
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		//ִ������Ĭ��
		iaddressService.setDefault(aid, uid, username);
		// ��Ӧ�ɹ�
		return new JsonResult<>(SUCCESS);
	}
	
	@RequestMapping("{aid}/delete")
	public JsonResult<Void> delete(@PathVariable("aid")Integer aid,HttpSession session){
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		iaddressService.delete(aid, uid, username);
		return new JsonResult<>(SUCCESS);
	}
	
}
