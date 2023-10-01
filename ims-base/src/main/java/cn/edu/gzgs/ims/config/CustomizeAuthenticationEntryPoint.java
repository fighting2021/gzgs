package cn.edu.gzgs.ims.config;

import cn.edu.gzgs.ims.common.ErrorCode;
import cn.edu.gzgs.ims.common.WrapperResult;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        /**
         * 用户未登录访问没有配置白名单的路径
         */
        WrapperResult wrapperResult = WrapperResult.fail(null, ErrorCode.LOGIN_TIME_EXPIRED.getMessage());
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(wrapperResult));
    }
}
