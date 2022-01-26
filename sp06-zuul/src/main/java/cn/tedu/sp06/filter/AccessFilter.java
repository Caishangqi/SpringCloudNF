package cn.tedu.sp06.filter;

import cn.tedu.web.util.JsonResult;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AccessFilter extends ZuulFilter {
    // 设置过滤器类型：pre, routing, post, error
    @Override
    public String filterType() {
        // return null;
        return FilterConstants.PRE_TYPE;
    }

    // 设置过滤器的顺序号
    // 在默认的第5个过滤器中，放入了 serviceId 所以
    // 后面的过滤器才能访问

    @Override
    public int filterOrder() {
        return 6;
    }

    /*
    针对当前请求，是否执行过滤代码
     */

    @Override
    public boolean shouldFilter() {

        // 调用 item-service 需要判断权限
        // 否则，不判断权限，直接跳过过滤代码

        // 获取请求上下文对象
        RequestContext currentContext = RequestContext.getCurrentContext();
        // 从上下文对象获得调用的服务id
        String serviceId = (String) currentContext.get(FilterConstants.SERVICE_ID_KEY);

        // 如果服务id是 item-service，返回true，否则返回false
        return "item-service".equalsIgnoreCase(serviceId);
    }

    // 过滤代码
    @Override
    public Object run() throws ZuulException {
        // http://localhost:3001/item-service/u4y544yy45?token=uyt343t3
        // 获取请求上下文对象
        RequestContext currentContext = RequestContext.getCurrentContext();
        // 从上下文获得 request 对象
        HttpServletRequest request = currentContext.getRequest();
        // 从 request 取出 token 参数
        String token = request.getParameter("token");
        // 如果没有 token, null, "", "   "
        if (StringUtils.isBlank(token)) {
            // 阻止继续调用
            currentContext.setSendZuulResponse(false); //不转发了
            // 直接向客户端返回响应
            // JsonResult - {code:400,msg:未登录,data:null}
            String json = JsonResult.build().code(400).msg("未登录").toString();
            currentContext.addZuulResponseHeader("Content-Type", "application/json;charset=UTF-8");
            currentContext.setResponseBody(json);

        }

        return null; //当前zuul版本中这个返回值不起任何作用

    }
}
