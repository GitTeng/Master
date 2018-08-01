package tv.huan.master.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.GrantedAuthority;
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
@Table(name = "role")
public class Role extends BaseEntity implements GrantedAuthority, ConfigAttribute {

    // Fields
    private String name;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "roleList", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User> userList = new ArrayList<User>();
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "role_resource", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "resource_id")})
    @OrderBy("id")
    @JsonIgnore
    private List<Resource> resourceList = new ArrayList<Resource>();

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return "Role [name=" + name + "]";
    }

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public String getAttribute() {
        return name;
    }
}
