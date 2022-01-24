package cn.tedu.sp04.service;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.sp01.service.OrderService;
import cn.tedu.sp04.feign.ItemClient;
import cn.tedu.sp04.feign.UserClient;
import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemClient itemClient;
    @Autowired
    private UserClient userClient;

    @Override
    public Order getOrder(String orderId) {
        log.info("获取订单，orderId="+orderId);

        /*
        1. 先获从注册表得到商品的主机地址 http://localhost:8001
        2. 拼接调用路径： http://localhost:8001/{orderId}
        3. 用参数值填充路径参数 http://localhost:8001/abc123
        4. 执行 REST API 请求
         */

        //远程调用商品，获取商品列表
        JsonResult<List<Item>> items = itemClient.getItems(orderId);
        //远程调用用户，获取用户数据
        JsonResult<User> user = userClient.getUser(8);

        Order order = new Order();
        order.setId(orderId);

        order.setItems(items.getData());
        order.setUser(user.getData());

        return order;
    }

    @Override
    public void addOrder(Order order) {

        log.info("添加订单："+order);

        // 减少商品库存
        itemClient.decreaseNumber(order.getItems());

        // 增加用户积分
        userClient.addScore(order.getUser().getId(), 1000);

    }
}
