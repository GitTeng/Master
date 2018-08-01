package tv.huan.master.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tv.huan.master.common.controller.BaseCRUDController;
import tv.huan.master.common.model.MyResponse;
import tv.huan.master.entity.Resource;
import tv.huan.master.entity.Role;
import tv.huan.master.security.UrlSecurityMetadataSource;
import tv.huan.master.service.ResourceService;
import tv.huan.master.service.RoleService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Role: warriorr
 * Mail: warriorr@163.com
 * Date: 2014/9/11
 * Time: 13:54
 * To change this template use File | Settings | File Templates
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseCRUDController<Role> {
    @Autowired
    RoleService roleService;
    @Autowired
    ResourceService resourceService;
    @Autowired
    UrlSecurityMetadataSource urlSecurityMetadataSource;

    @RequestMapping(value = "getResourceList", method = {RequestMethod.POST})
    @ResponseBody
    public MyResponse getResourceList(Long id) {
        MyResponse response = new MyResponse();
        List<Resource> resources = new ArrayList<Resource>();
        if (id != 0) {
            Role role = roleService.findById(id);
            resources = role.getResourceList();
        }
        List<Map<Object, Object>> lists = new ArrayList<>();
        Map<Object, Object> map;
        List<Resource> allResources = resourceService.findNotDelAll();
        //拼装树状图
        for (Resource resource : allResources) {
            map = new HashMap<>();
            map.put("id", resource.getId());
            map.put("pId", resource.getParentId());
            map.put("name", resource.getName());
            if (resource.getParentId() == 0) {
                map.put("open", "true");
            }
            for (Resource resource1 : resources) {
                if (resource1.getId() == resource.getId()) {
                    map.put("checked", true);
                }
            }
            lists.add(map);
        }
        response.setData(lists);
        return response;
    }

    @Override
    public MyResponse save(@Valid @ModelAttribute("m") Role role, BindingResult bindingResult, HttpServletRequest request) {
        MyResponse myResponse = new MyResponse();
        if (bindingResult.hasErrors()) {
            myResponse.setError("1");
            myResponse.setInfo("数据验证失败");
            myResponse.setData(bindingResult.getFieldErrors());
            return myResponse;
        }
        roleService.save(role);
        urlSecurityMetadataSource.loadResourceDefine();
        return myResponse;
    }

}
