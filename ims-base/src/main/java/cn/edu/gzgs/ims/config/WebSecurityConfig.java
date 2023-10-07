package cn.edu.gzgs.ims.config;

import cn.edu.gzgs.ims.common.ErrorCode;
import cn.edu.gzgs.ims.common.WrapperResult;
import cn.edu.gzgs.ims.domain.LoginUser;
import cn.edu.gzgs.ims.filter.JwtAuthenticationTokenFilter;
import cn.edu.gzgs.ims.filter.UserAndPwdLoginFilter;
import cn.edu.gzgs.ims.handlers.CustomAccessDeniedHandler;
import cn.edu.gzgs.ims.handlers.CustomizeLogoutSuccessHandler;
import cn.edu.gzgs.ims.utils.JwtUtil;
import cn.edu.gzgs.ims.utils.RedisCache;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Resource
    private SessionRegistry sessionRegistry;
    @Autowired
    private RedisCache redisCache;
    // 用户未登录处理逻辑
    @Autowired
    private CustomizeAuthenticationEntryPoint customizeAuthenticationEntryPoint;
    // 退出登录处理逻辑
    @Autowired
    private CustomizeLogoutSuccessHandler customizeLogoutSuccessHandler;
    // 自定义授权失败处理
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    //访问决策管理器
    @Autowired
    CustomizeAccessDecisionManager accessDecisionManager;
    //安全元数据源
    @Autowired
    CustomizeFilterInvocationSecurityMetadataSource securityMetadataSource;


    //白名单,访问有以下路径不需要登录
    private static final String[] whitelist = {
            "/swagger-ui.html"
            , "/swagger-resources/**"
            , "/webjars/springfox-swagger-ui/**"
            , "/v2/api-docs"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()//关闭csrf
            //不通过Session获取SecurityContext
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().authorizeRequests()
            // 对于登录接口 允许匿名访问
            .antMatchers(whitelist).anonymous()
            // 除上面外的所有请求全部需要鉴权认证
            .anyRequest().authenticated()
            .and().exceptionHandling()
            //未登录访问
            .authenticationEntryPoint(customizeAuthenticationEntryPoint)
            //权限不足
            .accessDeniedHandler(customAccessDeniedHandler);
        //授权配置
        http.authorizeRequests().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                o.setAccessDecisionManager(accessDecisionManager);    //访问决策管理器
                o.setSecurityMetadataSource(securityMetadataSource);  //安全元数据源
                return o;
            }
        });
        //登录处理逻辑(账户密码登录)
        http.addFilterAfter(userAndPwdSuccessFilter(), UsernamePasswordAuthenticationFilter.class);
        //指定jwtAuthenticationTokenFilter过滤器在UsernamePasswordAuthenticationFilter之前
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //设置退出登录处理器
        http.logout()//默认退出
            .logoutSuccessHandler(customizeLogoutSuccessHandler)
             //登出之后删除cookie
            .deleteCookies("JSESSIONID");
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    //配置认证管理且配置密码加密
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //加密方式
    @Bean
    public PasswordEncoder passwordEncoder() {
        //这里选择的是不可逆哈希算法
        return new BCryptPasswordEncoder();
    }

    /**
     * 账号密码登录处理
     */
    @Bean
    public UserAndPwdLoginFilter userAndPwdSuccessFilter() throws Exception {
        UserAndPwdLoginFilter filter = new UserAndPwdLoginFilter();
        filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest
                    , HttpServletResponse httpServletResponse, Authentication authentication)
                    throws IOException, ServletException {
                LoginUser loginUser = (LoginUser) authentication.getPrincipal();
                String uuid = UUID.randomUUID().toString();
                String jwt = JwtUtil.createJWT(uuid);
                //authenticate存入redis
                redisCache.setCacheObject("login:" + uuid, loginUser, 10, TimeUnit.MINUTES);
                System.out.println("user cache key: login:" + uuid);
                //把token响应给前端
                WrapperResult<Object> result = WrapperResult.success(jwt, "登录成功");
                System.out.println("Token: " + jwt);
                httpServletResponse.setContentType("text/json;charset=utf-8");
                //塞到HttpServletResponse中返回给前台
                httpServletResponse.getWriter().write(JSON.toJSONString(result));
            }
        });

        filter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request
                    , HttpServletResponse response, AuthenticationException e)
                    throws IOException, ServletException {
                //返回json数据
                WrapperResult result = null;
                if (e instanceof AccountExpiredException) {
                    //账号过期
                    result = WrapperResult.fail(null, ErrorCode.USER_ACCOUNT_EXPIRED.getMessage());
                } else if (e instanceof BadCredentialsException) {
                    //密码错误
                    result = WrapperResult.fail(null, ErrorCode.USER_CREDENTIALS_ERROR.getMessage());
                } else if (e instanceof CredentialsExpiredException) {
                    //密码过期
                    result = WrapperResult.fail(null, ErrorCode.USER_CREDENTIALS_EXPIRED.getMessage());
                } else if (e instanceof DisabledException) {
                    //账号不可用
                    result = WrapperResult.fail(null, ErrorCode.USER_ACCOUNT_DISABLE.getMessage());
                } else if (e instanceof LockedException) {
                    //账号锁定
                    result = WrapperResult.fail(null, ErrorCode.USER_ACCOUNT_LOCKED.getMessage());
                } else if (e instanceof InternalAuthenticationServiceException) {
                    //用户不存在
                    result = WrapperResult.fail(null, ErrorCode.USER_ACCOUNT_NOT_EXIST.getMessage());
                } else {
                    //其他错误
                    result = WrapperResult.fail(null, e.getMessage());
                }
                //处理编码方式，防止中文乱码的情况
                response.setContentType("text/json;charset=utf-8");
                //塞到HttpServletResponse中返回给前台
                response.getWriter().write(JSON.toJSONString(result));
            }
        });
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setSessionAuthenticationStrategy(new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry));
        return filter;
    }
}
