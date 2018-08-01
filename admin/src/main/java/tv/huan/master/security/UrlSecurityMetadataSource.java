package tv.huan.master.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import tv.huan.master.service.ResourceService;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

/**
 * 通过URL地址获取相应权限然后在获取相应的角色集合
 * <p>
 * 需要实现FilterInvocationSecurityMetadataSource接口
 *
 * @author baojulin
 */
@Service("urlSecurityMetadataSource")
public class UrlSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ResourceService resourceService;
     private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    @PostConstruct
//	被@PostConstruct修饰的方法会在服务器加载Servle的时候运行，并且只会被服务器执行一次。PostConstruct在构造函数之后执行,init()方法之前执行。
    // 在Web服务器启动时，提取系统中的所有权限。</span>
    public void loadResourceDefine() {
          resourceMap = resourceService.findResourceRole();
    }

    /**
     * 此方法就是通过url地址获取 角色信息的方法
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 获取当前的URL地址
        FilterInvocation filterInvocation = (FilterInvocation) object;
        String url = filterInvocation.getRequestUrl();
        logger.info("访问的URL地址为(包括参数):" + url);
        url = filterInvocation.getRequest().getServletPath();
        if (resourceMap == null) {
            loadResourceDefine();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null && !url.contains("login") && !url.contains("timeout")) {
            Collection collection = new LinkedList<>();
            ConfigAttribute configAttribute = new SecurityConfig("ROLE_NO_USER");
            collection.add(configAttribute);
            return collection;
        } else {
            return resourceMap.get(url);
        }
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * 如果为真则说明支持当前格式类型,才会到上面的 getAttributes 方法中
     */
    @Override
    public boolean supports(Class<?> clazz) {
        // 需要返回true表示支持
        return true;
    }

}
