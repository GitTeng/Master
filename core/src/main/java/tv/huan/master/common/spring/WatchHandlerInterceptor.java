package tv.huan.master.common.spring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2017/11/16 0016
 * Time: 16:27
 * To change this template use File | Settings | File Templates
 */
public class WatchHandlerInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LogManager.getLogger(WatchHandlerInterceptor.class);
    private int warnSlowTime;
    private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal("StopWatch-StartTime");

    public WatchHandlerInterceptor() {
    }

    public int getWarnSlowTime() {
        return this.warnSlowTime;
    }

    public void setWarnSlowTime(int warnSlowTime) {
        this.warnSlowTime = warnSlowTime;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long beginTime = System.currentTimeMillis();
        this.startTimeThreadLocal.set(beginTime);
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long endTime = System.currentTimeMillis();
        long beginTime = this.startTimeThreadLocal.get();
        long consumeTime = endTime - beginTime;
        if (consumeTime > (long) this.warnSlowTime) {
            logger.warn(String.format("%s consume %d millis", request.getRequestURI(), consumeTime));
        }

    }
}
