package cn.tedu.sp04.service;

import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Override
    public Order getOrder(String orderId) {
        log.info("获取订单，orderId="+orderId);

        //TODO: 远程调用商品，获取商品列表
        //TODO: 远程调用用户，获取用户数据

        Order order = new Order();
        order.setId(orderId);
        /*
        order.setItems();
        order.setUser();
        */
        return order;
    }

    @Override
    public void addOrder(Order order) {

        log.info("添加订单："+order);


    }
}
