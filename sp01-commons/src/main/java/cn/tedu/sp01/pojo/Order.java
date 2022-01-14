package cn.tedu.sp01.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String id; //y435t34t6-343453452-857345
    private User user;  //订单所属的用户
    private List<Item> items; //订单中购买的商品列表
}