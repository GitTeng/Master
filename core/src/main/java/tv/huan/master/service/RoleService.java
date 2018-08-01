package tv.huan.master.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tv.huan.master.common.dao.BaseDAO;
import tv.huan.master.common.service.BaseService;
import tv.huan.master.entity.Role;

/**
 * Created with IntelliJ IDEA.
 * Role: warriorr
 * Mail: warriorr@163.com
 * Date: 2014/8/8
 * Time: 17:02
 * To change this template use File | Settings | File Templates
 */
@Service
public class RoleService extends BaseService<Role> {
    @Autowired
    BaseDAO<Role> baseDAO;

    @Override
    protected Class baseClass() {
        return Role.class;
    }

}
