package tv.huan.master.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import tv.huan.master.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2014/8/8
 * Time: 16:54
 * To change this template use File | Settings | File Templates
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "resource")
public class Resource extends BaseEntity {
    private String url;
    private String name;
    private int parentId;
    private int isShowMenu = 0;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "resourceList", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Role> roleList = new ArrayList<Role>();

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getIsShowMenu() {
        return isShowMenu;
    }

    public void setIsShowMenu(int isShowMenu) {
        this.isShowMenu = isShowMenu;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}
