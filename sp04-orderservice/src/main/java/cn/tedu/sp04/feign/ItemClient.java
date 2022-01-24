package cn.tedu.sp04.feign;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.web.util.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Feign 其实集成了Ribbon(负载均衡 + 重试)
 */

/*
三点配置：
1. 调用哪个服务
2. 调用这个服务的哪个路径
3. 向这个路径提交什么参数
 */
@FeignClient(name = "ITEM-SERVICE")
public interface ItemClient {

    // 获取订单的商品列表
    @GetMapping("/{orderId}")
    JsonResult<List<Item>> getItems(@PathVariable String orderId);

    // 减少商品库存
    @PostMapping("/decreaseNumber")
    JsonResult<?> decreaseNumber(@RequestBody List<Item> items); //拼接完访问串，feignAPI直接执行远程调用

}
