package tv.huan.master.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Users: warriorr
 * Mail: warriorr@163.com
 * QQ:283173481
 * Date: 11-12-14
 * Time: 上午11:02
 * To change this template use File | Settings | File Templates
 */
@MappedSuperclass
public class BaseEntity {
    // 删除标记（0：正常；1：删除；
    public static final String DEL_FLAG_NORMAL = "0";
    public static final String DEL_FLAG_DELETE = "1";
    // cascade表示级联操作
// CascadeType.MERGE级联更新：若items属性修改了那么order对象保存时同时修改items里的对象。对应EntityManager的merge方法
// CascadeType.PERSIST级联刷新：获取order对象里也同时也重新获取最新的items时的对象。对应EntityManager的refresh(object)方法有效。即会重新查询数据库里的最新数据
// CascadeType.REFRESH级联保存：对order对象保存时也对items里的对象也会保存。对应EntityManager的presist方法
// CascadeType.REMOVE级联删除：对order对象删除也对items里的对象也会删除。对应EntityManager的remove方法
// FetchType.LAZY表示懒加载。对于xxxtoMany时即获得多的一方fetch的默认值是FetchType.LAZY懒加载。对于xxxtoOne时即获得一的一方fetch的默认值是FetchType.EAGER立即加载
// mappedBy="classes"就相当于inverse=true,(mappedBy指定的是不需要维护关系的一端)
//请检查orderItems这个属性是否使用了泛型,如:Set<Student>.
    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createDate = new Date();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date updateDate = new Date();
    protected String delFlag = DEL_FLAG_NORMAL;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
