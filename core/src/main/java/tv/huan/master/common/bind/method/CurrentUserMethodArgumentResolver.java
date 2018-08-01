package tv.huan.master.common.bind.method;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2017/5/4 0004
 * Time: 17:59
 * To change this template use File | Settings | File Templates
 */

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import tv.huan.master.common.Constants;
import tv.huan.master.common.bind.annotation.CurrentUser;
import tv.huan.master.entity.User;


/**
 * 增加方法注入，将含有 CurrentUser 注解的方法参数注入当前登录用户
 */
@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 如果参数类型是 User 并且有 CurrentUser 注解则支持
        return (parameter.getParameterType().isAssignableFrom(User.class)) &&
                parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 取出鉴权时存入的登录用户 Id
        Object object = webRequest.getAttribute(Constants.CURRENT_USER, RequestAttributes.SCOPE_REQUEST);
        if (object != null) {
            return object;
        } else {
            object = webRequest.getAttribute(Constants.CURRENT_USER, RequestAttributes.SCOPE_SESSION);
            if (object != null) {
                return object;
            }
        }
        throw new MissingServletRequestPartException(Constants.CURRENT_USER);
    }
}
