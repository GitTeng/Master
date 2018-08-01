package tv.huan.master.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import tv.huan.master.common.util.IpUtils;
import tv.huan.master.common.util.StringUtilsCustom;
import tv.huan.master.entity.SystemLog;
import tv.huan.master.entity.User;
import tv.huan.master.service.ResourceService;
import tv.huan.master.service.SystemLogService;
import tv.huan.master.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2014/8/11
 * Time: 13:21
 * To change this template use File | Settings | File Templates
 */
public class MyInterceptor extends HandlerInterceptorAdapter {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    SystemLogService systemLogService;

    @Autowired
    ResourceService resourceService;
    @Autowired
    UserService userService;

    /**
     * 最后执行，可用于释放资源
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception ex) throws Exception {
        String requestRri = request.getRequestURI();
        if (StringUtils.contains(requestRri, "/save") || StringUtils.contains(requestRri, "/update") || StringUtils.contains(requestRri, "/del") || ex != null) {
            User user = (User) request.getSession().getAttribute("user");
            if (user != null && user.getId() != null) {

                StringBuilder params = new StringBuilder();
                int index = 0;
                for (Object param : request.getParameterMap().keySet()) {
                    params.append((index++ == 0 ? "" : "&") + param + "=");
                    params.append(StringUtilsCustom.abbr(StringUtils.endsWithIgnoreCase((String) param, "password")
                            ? "" : request.getParameter((String) param), 100));
                }

                SystemLog log = new SystemLog();
                log.setType(ex == null ? SystemLog.TYPE_ACCESS : SystemLog.TYPE_EXCEPTION);
                log.setUser(user);
                log.setCreateDate(new Date());
                log.setRemoteAddr(IpUtils.getIpAddr(request));
                log.setUserAgent(request.getHeader("user-agent"));
                log.setRequestUri(request.getRequestURI());
                log.setMethod(request.getMethod());
                log.setParams(params.toString());
                log.setException(ex != null ? ex.toString() : "");
                log.setModule(ex != null ? ex.getMessage() : "");
                systemLogService.save(log);
            }
        }
    }

    /**
     * 显示视图前执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView view) throws Exception {
        String contextPath = request.getContextPath();
        if (view != null) {
            request.setAttribute("ctx", contextPath);
        }
    }

    /**
     * Controller之前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }
}
