package tv.huan.master.common.dao;

import tv.huan.master.common.model.PageResponse;
import tv.huan.master.common.model.PageRequest;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Users: warriorr
 * Mail: warriorr@163.com
 * QQ:283173481
 * Date: 11-12-14
 * Time: 上午10:59
 * To change this template use File | Settings | File Templates
 */
public interface BaseDAO<M> {
    public M load(Class BaseClass, Long id);

    public void insert(M base);
    public void save(M base);

    public void update(M base);

    public void delete(M base);

    public void delete(Class BaseClass, String id);

    public void logic_delete(Class baseClass, String ids);

    public List<M> find(String queryString);

    public List<M> find(String hql, Object[] param);

    public int findTotal(DetachedCriteria criteria);

    public List<M> find(DetachedCriteria criteria);

    public List<M> find(int page_num, int per_page, DetachedCriteria criteria);

    public M findUnique(DetachedCriteria criteria);

    public PageResponse find(PageRequest pageRequest, DetachedCriteria criteria);

    public PageResponse find(PageRequest pageRequest, final DetachedCriteria detachedCriteria, final String table);

    public void createSQLQuery(String sql);

}
