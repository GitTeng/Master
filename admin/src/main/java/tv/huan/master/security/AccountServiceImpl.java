package tv.huan.master.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tv.huan.master.service.UserService;

import javax.annotation.Resource;

/**
 * 登录操作，需要实现UserDetailsService接口
 *
 * @author baojulin
 */
@Service("accountService")
public class AccountServiceImpl implements UserDetailsService {
    @Resource
    private UserService userService;

    /**
     * 登录的时候，将用户信息存储到Account中
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userService.login(username);
        if (user == null) {
            throw new UsernameNotFoundException("UserName " + username + " not found");
        }
        return user;
    }
}
