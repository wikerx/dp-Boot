package com.scott.dp.common.activemq;

/**   
 * @ClassName:  OrderEntity   
 * @Description:模拟消息定义实体
 * @author: Mr.薛 
 * @date:   2019年5月22日 下午1:43:08     
 * @Copyright: 2019 
 * @Company: 自贸通
 */
public class OrderEntity {
	private int orderId;//商品编号
	private String orderNo;//商品编号
	private String orderName;//商品名称
	private double orderPrice;//商品价格
	private String orderNumber;//商品数量
	private String needAddress;//推送地址
	
	
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public double getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getNeedAddress() {
		return needAddress;
	}
	public void setNeedAddress(String needAddress) {
		this.needAddress = needAddress;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	

}
