package cn.tedu.sp06.fb;

import cn.tedu.web.util.JsonResult;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class OrderFB implements FallbackProvider {

    /*
    设置当前降级类，是针对哪个服务进行降级
       - order-service： 只针对订单降级 (如果对商品进行调用失败时会执行)
       - *：    对所有服务都应用当前降级类
       - null： 对所有服务都应用当前降级类
     */

    @Override
    public String getRoute() {
        return "order-service";
    }

    /*
    向客户端返回的响应
     */

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders = new HttpHeaders();
                //设置协议头 添加返回数据类型
                httpHeaders.add("Content-Type", "application/json;charset=UTF-8");
                return httpHeaders;
            }

            @Override
            public InputStream getBody() throws IOException {
                // JsonResult - {code:500,msg:调用后台服务出错,data:null}
                String msg = JsonResult.build().code(500).msg("调用后台服务出错").toString();
                // 把 json 封装到 ByteArrayInputStream
                return new ByteArrayInputStream(msg.getBytes("UTF-8"));
            }

            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.INTERNAL_SERVER_ERROR.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
            }

            @Override
            public void close() {

                // 关闭下面方法中的流
                // ByteArrayInputStream 不占用任何底层系统资源，
                // 所以不需要关闭

            }
        };
    }
}
