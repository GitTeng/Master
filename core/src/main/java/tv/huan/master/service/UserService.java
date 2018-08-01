package tv.huan.master.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tv.huan.master.common.dao.BaseDAO;
import tv.huan.master.common.service.BaseService;
import tv.huan.master.entity.User;

/**
 * Created by IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * QQ:283173481
 * Date: 11-12-14
 * Time: 上午11:31
 * To change this template use File | Settings | File Templates
 */
@Service
public class UserService extends BaseService<User> {
    @Autowired
    BaseDAO<User> baseDAO;

    @Override
    protected Class baseClass() {
        return User.class;
    }

    public User login(String loginName, String password) {
        DetachedCriteria crit = DetachedCriteria.forClass(User.class);
        crit.add(Restrictions.eq("loginName", loginName));
        crit.add(Restrictions.eq("password", password));
        crit.add(Restrictions.eq("delFlag", "0"));
        return baseDAO.findUnique(crit);
    }

    public User login(String loginName) {
        DetachedCriteria crit = DetachedCriteria.forClass(User.class);
        crit.add(Restrictions.eq("loginName", loginName));
        crit.add(Restrictions.eq("delFlag", "0"));
        return baseDAO.findUnique(crit);
    }
}
