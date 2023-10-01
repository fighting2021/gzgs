package cn.edu.gzgs.ims.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class UserAndPwdLoginFilter extends UsernamePasswordAuthenticationFilter {
    @Resource
    private SessionRegistry sessionRegistry;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            ObjectMapper mapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest = null;
            try (InputStream is = request.getInputStream()) {
                Map<String,String> authenticationBean = mapper.readValue(is, Map.class);
                String userName = authenticationBean.get("userName");
                String pwd = authenticationBean.get("pwd");
                if (userName == null) {
                    userName = "";
                }
                if (pwd == null) {
                    pwd = "";
                }
                request.setAttribute("userName", userName);
                authRequest = new UsernamePasswordAuthenticationToken(userName, pwd);
                sessionRegistry.registerNewSession(request.getSession().getId(), authRequest.getPrincipal());
            } catch (IOException e) {
                e.printStackTrace();
                authRequest = new UsernamePasswordAuthenticationToken("", "");
            }
            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
        else {
            return super.attemptAuthentication(request, response);
        }
    }
}
