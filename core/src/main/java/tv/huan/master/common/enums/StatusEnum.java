package tv.huan.master.common.enums;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2014/9/1
 * Time: 17:23
 * To change this template use File | Settings | File Templates
 */
public enum StatusEnum {
    SUCCESS("0", "操作成功"),
    ERROR("1", "访问失败"),
    REPEAT("2", "已存在"),
    AUTH_1000("1000", "旧密码输入错误"),
    ;
    private final String error;
    private final String info;

    StatusEnum(final String error, String info) {
        this.error = error;
        this.info = info;
    }

    public static StatusEnum valueof(String code) {
        for (StatusEnum f : StatusEnum.values()) {
            if (f.error == code) {
                return f;
            }
        }
        return null;
    }

    public String getError() {
        return error;
    }

    public String getInfo() {
        return info;
    }
}
