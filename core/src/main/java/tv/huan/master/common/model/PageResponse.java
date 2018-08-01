package tv.huan.master.common.model;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2015/1/23
 * Time: 17:51
 * To change this template use File | Settings | File Templates
 */
public class PageResponse {
    private int total;
    private Object rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }
}
