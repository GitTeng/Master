package tv.huan.master.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2017/3/16 0016
 * Time: 15:34
 * To change this template use File | Settings | File Templates
 */
@Service("myAuthenticationFailureHandler")
public class MyAuthenticationFailureHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException arg2)
            throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        // ajax 处理
        if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            response.setStatus(403);
        } else {
            response.setContentType("text/html;charset=UTF-8");
        }
        response.getWriter().print("无此操作权限，请于管理员联系");
        response.getWriter().close();
    }

}
