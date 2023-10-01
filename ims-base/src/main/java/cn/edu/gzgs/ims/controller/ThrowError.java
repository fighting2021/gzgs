package cn.edu.gzgs.ims.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ThrowError {
    @RequestMapping("/error/throw")
    public void rethrow(HttpServletRequest request) {
        throw (RuntimeException) request.getAttribute("filterError");
    }
}
