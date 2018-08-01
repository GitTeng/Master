package tv.huan.master.common.exception;


import tv.huan.master.common.enums.StatusEnum;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2017/6/22 0022
 * Time: 16:06
 * To change this template use File | Settings | File Templates
 */
public class MyException extends RuntimeException {
    private StatusEnum statusEnum;

    public MyException(String message) {
        super(message);
    }

    public MyException(StatusEnum statusEnum) {
        this.statusEnum = statusEnum;
    }

    public StatusEnum getStatusEnum() {
        return statusEnum;
    }

    public void setStatusEnum(StatusEnum statusEnum) {
        this.statusEnum = statusEnum;
    }

    @Override
    public String getMessage() {
        return statusEnum.getInfo();
    }
}
