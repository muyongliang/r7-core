package com.r7.core.common.filter;

import cn.hutool.json.JSONUtil;
import com.r7.core.common.holder.RequestHolder;
import com.r7.core.common.model.UserInfo;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 获取用户信息过滤器
 *
 * @author zhongpingli
 */
public class UserInfoFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String user = req.getHeader("user");
        // 如果有user头 说明此请求为保护请求获取用户信息
        // {"user_name":"18500127018","scope":["all"],"appId":null,"organId":0,"id":1316287064941293568,
        // "exp":1603272959,"authorities":["ADMIN"],"jti":"a2806820-6862-48e2-bfd3-91595dbabf7d","client_id":"uim"}
        if (user != null) {
            UserInfo userInfo = JSONUtil.toBean(user, UserInfo.class);
            RequestHolder.addUserInfo(userInfo);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
