package tv.huan.master.common.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Repository;
import tv.huan.master.common.dao.BaseDAO;
import tv.huan.master.common.model.PageRequest;
import tv.huan.master.common.model.PageResponse;
import tv.huan.master.common.util.LocalQInterceptor;

import javax.annotation.Resource;
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
@SuppressWarnings("unchecked")
@Repository
public class BaseDAOImpl<M> implements BaseDAO<M> {
    public static final String LOGIC_DELETE_ALL_QUERY_STRING = "update %s x set x.del_flag=1 where x.id in (:ids)";
    public static final String DELETE_ALL_QUERY_STRING = "delete from %s x where x.id in (:ids)";
    @Resource
    private SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public M load(Class baseClass, Long id) {
        return (M) this.getCurrentSession().get(baseClass, id);
    }

    public void insert(M base) {
        this.getCurrentSession().save(base);
    }

    public void save(M base) {
        this.getCurrentSession().saveOrUpdate(base);
    }

    public void update(M base) {
        this.getCurrentSession().update(base);
    }

    public void delete(M base) {
        this.getCurrentSession().delete(base);
    }

    public void delete(Class baseClass, String ids) {
        String hql = String.format(DELETE_ALL_QUERY_STRING, baseClass.getSimpleName());
        Query qry = this.getCurrentSession().createQuery(hql);
        String[] strids = ids.split(",");
        Integer[] intids = new Integer[strids.length];
        for (int i = 0; i < strids.length; i++) {
            intids[i] = Integer.parseInt(strids[i]);
        }
        qry.setParameterList("ids", intids, IntegerType.INSTANCE);
        qry.executeUpdate();
    }

    public void logic_delete(Class baseClass, String ids) {
        String hql = String.format(LOGIC_DELETE_ALL_QUERY_STRING, baseClass.getSimpleName());
        Query qry = this.getCurrentSession().createQuery(hql);
        String[] strids = ids.split(",");
        Integer[] intids = new Integer[strids.length];
        for (int i = 0; i < strids.length; i++) {
            intids[i] = Integer.parseInt(strids[i]);
        }
        qry.setParameterList("ids", intids, IntegerType.INSTANCE);
        qry.executeUpdate();
    }

    public List<M> find(String hql) {
        return this.getCurrentSession().createQuery(hql).list();
    }

    public List<M> find(String hql, Object[] param) {
        Query qry = this.getCurrentSession().createQuery(hql);
        setQueryParams(qry, param);
        return qry.list();
    }

    public M findUnique(DetachedCriteria detachedCriteria) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(this.getCurrentSession());
        return (M) criteria.uniqueResult();
    }

    @Override
    public int findTotal(DetachedCriteria detachedCriteria) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(this.getCurrentSession());
        return ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
    }

    public List<M> find(DetachedCriteria detachedCriteria) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(this.getCurrentSession());
        return criteria.list();
    }

    public List<M> find(int page_num, int per_page, DetachedCriteria detachedCriteria) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(this.getCurrentSession());
        criteria.setFirstResult((page_num - 1) * per_page);
        criteria.setMaxResults(per_page);
        return criteria.list();
    }

    public PageResponse find(PageRequest pageRequest, DetachedCriteria detachedCriteria) {
        PageResponse pageResponse = new PageResponse();
        Criteria criteria = detachedCriteria.getExecutableCriteria(this.getCurrentSession());
        int total = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setFirstResult(pageRequest.getFirstResult());
        criteria.setMaxResults(pageRequest.getPageSize());
        pageResponse.setTotal(total);
        pageResponse.setRows(criteria.list());
        return pageResponse;
    }

    public PageResponse find(PageRequest pageRequest, DetachedCriteria detachedCriteria, String table) {
        PageResponse pageResponse = new PageResponse();
        LocalQInterceptor localQInterceptor = new LocalQInterceptor();
        localQInterceptor.setLangTable(table);
        Criteria criteria = detachedCriteria.getExecutableCriteria(this.getCurrentSession());
        int total = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
        criteria.setProjection(null);
        criteria.setFirstResult(pageRequest.getFirstResult());
        criteria.setMaxResults(pageRequest.getPageSize());
        pageResponse.setTotal(total);
        pageResponse.setRows(criteria.list());
        return pageResponse;
    }

    public void createSQLQuery(String sql) {
        this.getCurrentSession().createNativeQuery(sql).executeUpdate();
    }

    public void setQueryParams(Query qry, Object[] params) {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                qry.setParameter(i, params[i]);
            }
        }
    }
}
