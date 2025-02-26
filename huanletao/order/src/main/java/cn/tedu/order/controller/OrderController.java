package cn.tedu.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huanletao.common.pojo.Order;
import com.huanletao.common.vo.SysResult;

import cn.tedu.order.service.OrderService;

@RestController
@RequestMapping("/order/manage")
public class OrderController {
	@Autowired
	private OrderService orderService;
	//查询我的订单
	@RequestMapping("query/{userId}")
	public List<Order> queryMyOrders(@PathVariable 
			String userId){
		return orderService.queryMyOrders(userId);
	}
	@RequestMapping("query1/{userId}")
	public List<Order> queryMyOrders1(@PathVariable 
			String userId){
		return orderService.queryMyOrders1(userId);
	}
	//新增订单
	@RequestMapping("save")
	public SysResult saveOrder(Order order){
		try{
			
			orderService.saveOrder(order);
			return SysResult.ok();
		}catch(Exception e){
			e.printStackTrace();
			return SysResult.build(201, "", null);
		}
	}
	
	//删除
	@RequestMapping("delete/{orderId}")
	public SysResult deleteOrder(@PathVariable String
			 orderId){
		try{
			orderService.deleteOrder(orderId);
			return SysResult.ok();
		}catch(Exception e){
			e.printStackTrace();
			return SysResult.build(201, "", null);
		}
		
	}
	
	//支付状态改变
	@RequestMapping("paystate/{orderId}")
	public SysResult insertPaystate(@PathVariable String orderId){
		try {
			orderService.updatePaystate(orderId);
			return SysResult.ok();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return SysResult.build(201, e.getMessage(), null);
		}
	}
	
	
	
	
	
	
	
}
