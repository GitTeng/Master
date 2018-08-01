package tv.huan.master.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tv.huan.master.common.bind.annotation.CurrentUser;
import tv.huan.master.common.controller.BaseCRUDController;
import tv.huan.master.common.enums.StatusEnum;
import tv.huan.master.common.model.MyResponse;
import tv.huan.master.common.util.SecureUtil;
import tv.huan.master.entity.User;
import tv.huan.master.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA. User: warriorr Mail: warriorr@163.com Date:
 * 2014/8/11 Time: 14:32 To change this template use File | Settings | File
 * Templates
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseCRUDController<User> {
    @Autowired
    UserService userService;

    @RequestMapping(value = "updatePW", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public MyResponse updatePW(HttpServletRequest request, @CurrentUser User user) {
        MyResponse response = new MyResponse();
        String aa = user.getPassword();
        String bb = SecureUtil.md5(request.getParameter("old_password"));
        if (aa.equals(bb)) {
            user.setPassword(SecureUtil.md5(request.getParameter("new_password")));
            userService.save(user);
            return response;
        } else {
            response.setEnum(StatusEnum.AUTH_1000);
            return response;
        }
    }

}
