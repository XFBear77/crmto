package cn.tedu.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Product;
import cn.tedu.store.service.IProductService;
import cn.tedu.store.util.JsonResult;
/**
 * ������Ʒ�����������Ŀ���ǰ
 */
@RestController
@RequestMapping("products")
public class ProductController extends BaseController {
	
	@Autowired
	private IProductService  productService ;
	
	@GetMapping("hot")
	public JsonResult<List<Product>> getHotList(){
		//����ҵ�������ѯ����
		List<Product> data = productService.getHostList();
		//��Ӧ�ɹ�������
		return new JsonResult<>(SUCCESS,data);
	}
	
	@GetMapping("{id}/details")
	public JsonResult<Product> getById(@PathVariable("id")Integer id){
		//ִ�в�ѯ
		Product data = productService.getById(id);
		//��Ӧ�ɹ�������ѯ���
		return new JsonResult<>(SUCCESS,data);
	}
}
