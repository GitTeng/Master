package tv.huan.master.entity;

import tv.huan.master.common.entity.BaseEntity;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2015/4/20
 * Time: 15:12
 * To change this template use File | Settings | File Templates
 */
@Entity
@Table(name = "systemlog")
public class SystemLog extends BaseEntity {
    private String type; 		// 日志类型（1：操作日志；2：错误日志）
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = User.class)
    @JoinColumn(name = "user_id")
//    @JSONField(serialzeFeatures={SerializerFeature.DisableCircularReferenceDetect})
    private User user;		// 创建者
    private String remoteAddr; 	// 操作用户的IP地址
    private String requestUri; 	// 操作的URI
    private String method; 		// 操作的方式
    @Column(length = 1000)
    private String params; 		// 操作提交的数据
    private String module;			//模块
    private String userAgent;	// 操作用户代理信息
    @Column(length = 1000)
    private String exception; 	// 异常信息

    public static final String TYPE_ACCESS = "1";
    public static final String TYPE_EXCEPTION = "2";

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
