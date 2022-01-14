package cn.tedu.sp01.service;
import cn.tedu.sp01.pojo.Order;
public interface OrderService {
    //获取订单
    Order getOrder(String orderId);
    //创建订单
    void addOrder(Order order);
}