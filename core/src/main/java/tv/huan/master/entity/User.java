package tv.huan.master.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tv.huan.master.common.entity.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "user")
public class User extends BaseEntity implements UserDetails {
//    @Version
//    @Column(nullable = false)
//    private Long version;
    @NotNull(message = "{not.null}")
    private String loginName;// 登录名
    private String password;
    @Length(min = 11,max = 11,message = "{length.not.valid}")
    private String phone;
    private String email;
    private String realName;
    private String address;
    private Integer age;
    private String loginIp;    // 最后登陆IP
    private Date loginDate;    // 最后登陆日期
//    如果是EAGER，那么表示取出这条数据时，它关联的数据也同时取出放入内存中
//    如果是LAZY，那么取出这条数据时，它关联的数据并不取出来，在同一个session中，什么时候要用，就什么时候取(再次访问数据库)。
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @OrderBy("id")
    private List<Role> roleList = new ArrayList<Role>();

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User [loginName=" + loginName + ", password=" + password + ", phone=" + phone + ", email=" + email
                + ", fullName=" + realName + ", address=" + address + ", loginIp=" + loginIp + ", loginDate="
                + loginDate + ", roleList=" + roleList + "]";
    }

    /**
     * 返回角色集合
     */
    @Transient
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleList;
    }

    /**
     * 返回用户名
     */
    @Transient
    @Override
    public String getUsername() {
        return loginName;
    }

    /**
     * 账户是否没有过期
     */
    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 是否没有被锁
     */
    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 用户的凭证是否没有过期
     */
    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否激活
     */
    @Transient
    @Override
    public boolean isEnabled() {
        return delFlag.equals("0");
    }
}
