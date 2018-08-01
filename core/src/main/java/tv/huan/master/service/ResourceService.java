package tv.huan.master.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.stereotype.Service;
import tv.huan.master.common.dao.BaseDAO;
import tv.huan.master.common.service.BaseService;
import tv.huan.master.entity.Resource;
import tv.huan.master.entity.Role;
import tv.huan.master.entity.User;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Resource: warriorr
 * Mail: warriorr@163.com
 * Date: 2014/8/9
 * Time: 11:33
 * To change this template use File | Settings | File Templates
 */
@Service
public class ResourceService extends BaseService<Resource> {
    @Autowired
    BaseDAO<Resource> baseDAO;

    /**
     * 查找一级分类
     *
     * @return
     */
    public List<Resource> findToSelect() {
        DetachedCriteria crit = DetachedCriteria.forClass(Resource.class);
        crit.add(Restrictions.eq("type", 0));
        return baseDAO.find(crit);
    }

    /**
     * 按照用户查找资源
     *
     * @param user
     * @return
     */
    public List<Resource> findResourceList(User user) {
        Set<Long> set = new HashSet<Long>();
        List<Resource> newList = new ArrayList<Resource>();
        for (Role role : user.getRoleList()) {
            for (Resource resource : role.getResourceList()) {
                if (!set.contains(resource.getId())) {
                    set.add(resource.getId());
                    newList.add(resource);
                }
            }
        }
        return newList;
    }

    public List<Role> findRoleList(String url) {
        DetachedCriteria crit = DetachedCriteria.forClass(Resource.class);
        crit.add(Restrictions.eq("url", url));
        List<Resource> resourceList = baseDAO.find(crit);
        if (resourceList.size() == 0) {
            return null;
        } else {
            return resourceList.get(0).getRoleList();
        }
    }
    public List resourceGroup(List<Resource> list){
        List parentList = new ArrayList();
        for (Resource resource : list) {
            if (resource.getParentId() == 0) {
                Map<String, Object> parentMap = new HashMap<String, Object>();
                parentMap.put("id", resource.getId());
                parentMap.put("text", resource.getName());

                // 递归创建子节点
                List childList = createResourceTreeChildren(list,resource.getId());

                parentMap.put("child", childList);
                parentList.add(parentMap);
            }
        }
        return parentList;
    }

    public Map<String, Collection<ConfigAttribute>> findResourceRole() {
        List<Resource> list = this.findAll();
        Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

        for (Resource resource : list) {
            List<Role> roleList = resource.getRoleList();
            Collection<ConfigAttribute> roles = new ArrayList<ConfigAttribute>();
            for (Role role : roleList) {
                roles.add(role);
            }
            resourceMap.put(resource.getUrl(), roles);
        }
        return resourceMap;
    }

    public List<Map<String, Object>> createResourceTreeChildren(List<Resource> list, Long fid) {
        List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = null;
            Resource treeChild = (Resource) list.get(i);
            if (treeChild.getParentId() == fid) {
                map = new HashMap<String, Object>();
                //这里必须要将对象角色的id、name转换成ComboTree在页面的显示形式id、text
                map.put("id", list.get(i).getId());
                map.put("text", list.get(i).getName());
                map.put("url", list.get(i).getUrl());
                map.put("children", createResourceTreeChildren(list, treeChild.getId()));
            }

            if (map != null)
                childList.add(map);
        }
        return childList;
    }

    @Override
    protected Class baseClass() {
        return Resource.class;
    }
}
