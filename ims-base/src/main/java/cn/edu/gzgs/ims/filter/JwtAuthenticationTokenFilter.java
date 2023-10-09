package cn.edu.gzgs.ims.filter;

import cn.edu.gzgs.ims.common.ErrorCode;
import cn.edu.gzgs.ims.domain.LoginUser;
import cn.edu.gzgs.ims.utils.JwtUtil;
import cn.edu.gzgs.ims.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            //放行
            filterChain.doFilter(request, response);
            return;
        }
        //解析token
        String uuid;
        try {
            uuid = JwtUtil.getUserId(token);
        } catch (Exception e) {
            request.setAttribute("filterError",  new RuntimeException(ErrorCode.LOGIN_TIME_EXPIRED.getMessage()));
            request.getRequestDispatcher("/error/throw").forward(request,response);
            return;
        }
        //从redis中获取用户信息
        String redisKey = "login:" + uuid;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        if (Objects.isNull(loginUser)) {
            request.setAttribute("filterError",  new RuntimeException(ErrorCode.LOGIN_TIME_EXPIRED.getMessage()));
            request.getRequestDispatcher("/error/throw").forward(request,response);
            return;
        }
        //存入SecurityContextHolder
        //获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
