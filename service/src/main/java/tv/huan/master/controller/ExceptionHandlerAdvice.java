package tv.huan.master.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import tv.huan.master.common.exception.MyException;
import tv.huan.master.common.model.MyResponse;
import tv.huan.master.common.util.LogUtils;

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
    @ResponseBody
    public MyResponse exception(Exception ex) {
        logger.error(ex.getMessage());
        MyResponse myResponse = new MyResponse();
        if (ex instanceof MyException) {
            MyException myException = (MyException) ex;
            myResponse.setEnum(myException.getStatusEnum());
        } else {
            ex = (Exception) LogUtils.showTraces(ex);
            logger.error(ex.getMessage());
            myResponse.setError("1");
            myResponse.setInfo(ex.getMessage());
        }
        return myResponse;
    }
}