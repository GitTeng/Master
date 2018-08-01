package tv.huan.master.common.model;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2015/1/23
 * Time: 17:27
 * To change this template use File | Settings | File Templates
 */
public class PageRequest implements java.io.Serializable {
    private static final long serialVersionUID = 7232798260610351343L;
    private int pageNumber; //当前页
    private int pageSize; //每页大小
    private String sort; //排序字段
    private String order; //排序规则

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getFirstResult() {
        return (pageNumber - 1) * pageSize;
    }
}
