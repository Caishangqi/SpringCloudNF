package cn.tedu.sp02.controller;
import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.service.ItemService;
import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Random;

@Slf4j
@RestController
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/{orderId}")
    public JsonResult<List<Item>> getItems(
            @PathVariable String orderId) throws InterruptedException {
        List<Item> items = itemService.getItems(orderId);

        // 模拟阻塞运算
        if (Math.random() < 0.9) {  //90%概率执行阻塞代码
            //阻塞时长随机 0 到 5 秒
            int t = new Random().nextInt(5000);
            log.info("阻塞： "+t);
            Thread.sleep(t);
        }


        return JsonResult.build().code(200).data(items);
    }
    /*
    @RequestBody
       - 客户端提交的数据，放在 post 请求协议体中
       - 服务器端从提交的http协议体，完整接收商品列表数据
       [{id:1,name:xx,number:3}, {}, {} ....]
     */
    @PostMapping("/decreaseNumber")
    public JsonResult<?> decreaseNumber(
            @RequestBody List<Item> items) {
        itemService.decreaseNumber(items);
        return JsonResult.build().code(200).msg("减少库存成功");
    }

    // 处理浏览器自动发起的图标文件请求，避免干扰其他路径的测试
    @GetMapping("/favicon.ico")
    public void ico() {
    }
}
