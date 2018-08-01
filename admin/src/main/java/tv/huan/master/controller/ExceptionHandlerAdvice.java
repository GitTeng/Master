package tv.huan.master.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import tv.huan.master.common.exception.MyException;
import tv.huan.master.common.model.MyResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2017/3/23 0023
 * Time: 16:33
 * To change this template use File | Settings | File Templates
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(value = Exception.class)
    public ModelAndView exception(Exception ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.error(ex.getMessage());
        MyResponse myResponse = new MyResponse();
        if (ex instanceof MyException) {
            MyException myException = (MyException) ex;
            myResponse.setEnum(myException.getStatusEnum());
        } else {
            ex = (Exception) showTraces(ex);
            logger.error(ex.getMessage());
            myResponse.setError("1");
            myResponse.setInfo(ex.getMessage());
        }
        // 判断是否 Ajax 请求
        if ((request.getHeader("accept").contains("application/json") ||
                (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").contains("XMLHttpRequest")))) {
            response.setStatus(500);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(JSON.toJSONString(myResponse));
            response.getWriter().close();
            return null;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error");
            modelAndView.addObject("error", myResponse.getInfo());
            return modelAndView;
        }
    }

    private Throwable showTraces(Throwable t) {
        Throwable next = t.getCause();
        if (next == null) {
            return t;
        } else {
            return showTraces(next);
        }
    }
}