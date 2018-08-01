package tv.huan.master.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import tv.huan.master.common.controller.BaseCRUDController;
import tv.huan.master.entity.Resource;
import tv.huan.master.service.ResourceService;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2014/9/16
 * Time: 13:22
 * To change this template use File | Settings | File Templates
 */
@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseCRUDController<Resource> {
    @Autowired
    ResourceService resourceService;

}
