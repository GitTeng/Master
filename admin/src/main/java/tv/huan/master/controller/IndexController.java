package tv.huan.master.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tv.huan.master.common.Constants;
import tv.huan.master.service.ResourceService;
import tv.huan.master.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2014/8/12
 * Time: 14:50
 * To change this template use File | Settings | File Templates
 */
@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    UserService userService;
    @Autowired
    ResourceService resourceService;

    @RequestMapping(value = "login", method = {RequestMethod.POST, RequestMethod.GET})
    public String login(@RequestParam(value = "error", required = false) String error, HttpServletRequest request) {
        if (error != null) {
            request.setAttribute("error", error);
        }
        return "login";
    }

    @RequestMapping(value = "main", method = {RequestMethod.GET, RequestMethod.POST})
    public String main(HttpSession session, Model model) {
        model.addAttribute("menus", session.getAttribute(Constants.CURRENT_MENUS));
        return "main";
    }


    @RequestMapping(value = "/timeout")
    public void sessionTimeout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getHeader("x-requested-with") != null
                && request.getHeader("x-requested-with").equalsIgnoreCase(
                "XMLHttpRequest")) { // ajax 超时处理
            response.setStatus(500);
            response.getWriter().print("timeout");  //设置超时标识
            response.getWriter().close();
        } else {
            response.sendRedirect("login?error=login.timeout");
            response.getWriter().close();
        }
    }
}
