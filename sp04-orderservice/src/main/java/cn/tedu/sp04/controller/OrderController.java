package cn.tedu.sp04.controller;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.sp01.service.OrderService;
import cn.tedu.web.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{orderId}")
    public JsonResult<Order> getOrder(
            @PathVariable String orderId) {
        Order order = orderService.getOrder(orderId);
        return JsonResult.build().code(200).data(order);
    }

    @GetMapping("/add")
    public JsonResult<?> addOrder() {
        Order order = new Order();
        order.setId("uy45t3457ijuhy");
        order.setUser(new User(8,null,null));
        order.setItems(Arrays.asList(new Item[]{
                new Item(1,"商品1",1),
                new Item(2,"商品2",6),
                new Item(3,"商品3",1),
                new Item(4,"商品4",2),
                new Item(5,"商品5",5)
        }));
        orderService.addOrder(order);
        return JsonResult.build().code(200).msg("添加订单成功");
    }

    @GetMapping("/favicon.ico")
    public void ico() {
    }

}
