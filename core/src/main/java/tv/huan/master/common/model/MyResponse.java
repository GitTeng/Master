package tv.huan.master.common.model;

import tv.huan.master.common.enums.StatusEnum;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2014/12/10
 * Time: 15:26
 * To change this template use File | Settings | File Templates
 */
public class MyResponse<M> {
    public MyResponse() {
    }

    public MyResponse(StatusEnum statusEnum) {
        setError(statusEnum.getError());
        setInfo(statusEnum.getInfo());
    }

    private String error = StatusEnum.SUCCESS.getError();
    private String info = StatusEnum.SUCCESS.getInfo();
    private M data;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setEnum(StatusEnum statusEnum) {
        setError(statusEnum.getError());
        setInfo(statusEnum.getInfo());
    }

    public M getData() {
        return data;
    }

    public void setData(M data) {
        this.data = data;
    }
}
