package com.xuecheng.govern.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//此过滤器作用，判断请求头部信息是否有Authorization,如果没有拒绝访问，否则转发到微服务
//@Component
public class LoginFilterTest extends ZuulFilter {

    /**
     * filterType：返回字符串代表过滤器的类型，如下
     * pre：请求在被路由之前执行
     * routing：在路由请求时调用
     * post：在routing和errror过滤器之后调用
     * error：处理请求时发生错误调用
     *
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * filterOrder：此方法返回整型数值，通过此数值来定义过滤器的执行顺序，数字越小优先级越高。
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    //shouldFilter：返回一个Boolean值，判断该过滤器是否需要执行。返回true表示要执行此过虑器，否则不执
    @Override
    public boolean shouldFilter() {
        return true;
    }

    //run：过滤器的业务逻辑
    ///此过滤器作用，判断请求头部信息是否有Authorization,如果没有拒
    @Override
    public Object run() throws ZuulException {
        //1.获取request
        RequestContext requestContext= RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse response =requestContext.getResponse();

        //得到Authorization头
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization)){
            //拒绝访问
            requestContext.setSendZuulResponse(false);
            //设置响应代码及信息
            requestContext.setResponseStatusCode(200);
            ResponseResult responseResult =new ResponseResult(CommonCode.UNAUTHENTICATED);
            //对象转换json
            String jsonstring = JSON.toJSONString(responseResult);
            requestContext.setResponseBody(jsonstring);
            //转成json,设置contenType
            response.setContentType("application/json;charset=utf-8");
            return null;
        }

        return null;
    }
}
