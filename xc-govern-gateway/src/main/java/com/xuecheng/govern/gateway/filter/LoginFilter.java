package com.xuecheng.govern.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.govern.gateway.service.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//此过滤器作用，判断请求头部信息是否有Authorization,如果没有拒绝访问，否则转发到微服务
@Component
public class LoginFilter extends ZuulFilter {


    @Autowired
    AuthService authService;

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
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        //查询身份令牌
        String access_token = authService.getTokenFromCookie(request);
        if (access_token == null) {
            //拒绝访问
            access_denied();
            return null;
        }
        //从redis校验令牌是否过期
        long expire = authService.getExpire(access_token);
        if (expire <= 0) {
            //拒绝访问
            access_denied();
            return null;
        }
        //查询jwt令牌
        String jwt = authService.getJwtFormHeader(request);
            if (jwt == null) {
                access_denied();
                return null;
            }
            return null;
        }


    private void access_denied(){
            RequestContext requestContext= RequestContext.getCurrentContext();
            requestContext.setSendZuulResponse(false);//拒绝访问
            HttpServletResponse response = requestContext.getResponse();
            //设置相应内容
            //设置响应代码及信息
            requestContext.setResponseStatusCode(200);
            ResponseResult responseResult =new ResponseResult(CommonCode.UNAUTHENTICATED);
            //对象转换json
            String jsonstring = JSON.toJSONString(responseResult);
            requestContext.setResponseBody(jsonstring);
            //转成json,设置contenType
            response.setContentType("application/json;charset=utf-8");
        }
}