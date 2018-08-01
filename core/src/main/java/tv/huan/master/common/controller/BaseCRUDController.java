package tv.huan.master.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tv.huan.master.common.Constants;
import tv.huan.master.common.entity.BaseEntity;
import tv.huan.master.common.enums.StatusEnum;
import tv.huan.master.common.model.PageResponse;
import tv.huan.master.common.model.PageRequest;
import tv.huan.master.common.model.MyResponse;
import tv.huan.master.common.model.Searchable;
import tv.huan.master.common.service.BaseService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2015/1/27
 * Time: 14:10
 * To change this template use File | Settings | File Templates
 */
public class BaseCRUDController<M extends BaseEntity> extends BaseController {
    @Autowired
    BaseService<M> baseService;

    @RequestMapping(value = "list", method = {RequestMethod.GET})
    public String showListPage() {
        return viewName("list");
    }

    @RequestMapping(value = "list", method = {RequestMethod.POST})
    @ResponseBody
    public PageResponse showListPage(PageRequest pageRequest, Searchable searchable) {
        return baseService.find(pageRequest, searchable);
    }

    @RequestMapping(value = "getAll", method = {RequestMethod.POST})
    @ResponseBody
    public MyResponse getAll() {
        MyResponse response = new MyResponse();
        response.setData(baseService.findNotDelAll());
        return response;
    }

    @RequestMapping(value = "create", method = {RequestMethod.GET})
    public String showCreateForm(Model model) {
        model.addAttribute(Constants.OP_NAME, "create");
        return viewName("editForm");
    }

    @RequestMapping(value = "update", method = {RequestMethod.GET})
    public String showUpdateForm(Model model) {
        model.addAttribute(Constants.OP_NAME, "update");
        return viewName("editForm");
    }

    @RequestMapping(value = "updateState", method = {RequestMethod.POST})
    @ResponseBody
    public MyResponse updateState(Long id, String delFlag) {
        MyResponse myResponse = new MyResponse<>();
        try {
            M m = baseService.findById(id);
            m.setDelFlag(delFlag);
            baseService.save(m);
        } catch (Exception e) {
            myResponse.setEnum(StatusEnum.ERROR);
        }
        return myResponse;
    }

    @RequestMapping(value = "view", method = {RequestMethod.GET})
    public String view(@RequestParam("id") M m, Model model) {
        model.addAttribute(Constants.OP_NAME, "view");
        return viewName("editForm");
    }

    @RequestMapping(value = "save", method = {RequestMethod.POST})
    @ResponseBody
    public MyResponse save(@Valid @ModelAttribute("m") M m, BindingResult bindingResult, HttpServletRequest request) {
        MyResponse myResponse = new MyResponse();
        if (bindingResult.hasErrors()) {
            myResponse.setError("1");
            myResponse.setInfo("数据验证失败");
            myResponse.setData(bindingResult.getFieldErrors());
            return myResponse;
        }

        baseService.save(m);
        return myResponse;
    }

    @RequestMapping(value = "del", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public MyResponse del(String ids) {
        MyResponse myResponse = new MyResponse();
        baseService.del_logic(ids);
        return myResponse;
    }

    @RequestMapping(value = "checkValue", method = {RequestMethod.POST})
    @ResponseBody
    public MyResponse checkValue(String name, String value) {
        MyResponse myResponse = new MyResponse();
        List<M> list = baseService.findByNameAndValue(name, value);
        if (list.size() > 0) {
            myResponse.setEnum(StatusEnum.REPEAT);
            myResponse.setInfo(value + myResponse.getInfo());
        }
        return myResponse;
    }
}
