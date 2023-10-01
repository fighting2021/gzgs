package cn.edu.gzgs.ims.handlers;

import cn.edu.gzgs.ims.common.ErrorCode;
import cn.edu.gzgs.ims.common.WrapperResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;


//全局异常处理
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public WrapperResult<Object> exceptionHandler(HandlerMethod handlerMethod, Exception e){
        WrapperResult res =new WrapperResult();
        logger.error("调用目标方法:"+handlerMethod+"出现异常 ",e);
        res.setCode(ErrorCode.FAIL.getCode());
        res.setMessage("调用目标服务异常，请联系管理员查看后台日志信息");
        return res;
    }

    @ExceptionHandler(value = RuntimeException.class)
    public WrapperResult<Object> exceptionHandler(HandlerMethod handlerMethod, RuntimeException e){
        WrapperResult res =new WrapperResult();
        logger.error("调用目标方法:"+handlerMethod+"出现异常 ",e);
        res.setCode(ErrorCode.FAIL.getCode());
        res.setMessage(e.getMessage());
        return res;
    }
}
