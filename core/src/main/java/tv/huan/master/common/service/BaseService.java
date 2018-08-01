package tv.huan.master.common.service;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tv.huan.master.common.dao.BaseDAO;
import tv.huan.master.common.entity.BaseEntity;
import tv.huan.master.common.enums.SearchOperator;
import tv.huan.master.common.model.PageRequest;
import tv.huan.master.common.model.PageResponse;
import tv.huan.master.common.model.Searchable;
import tv.huan.master.common.util.BeanUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Ts: warriorr
 * Mail: warriorr@163.com
 * QQ:283173481
 * Date: 11-12-14
 * Time: 上午11:03
 * To change this template use File | Settings | File Templates
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public abstract class BaseService<M extends BaseEntity> {
    @Autowired
    BaseDAO<M> baseDAO;

    abstract protected Class baseClass();

    public List<M> findAll() {
        DetachedCriteria crit = DetachedCriteria.forClass(baseClass());
        return baseDAO.find(crit);
    }

    public List<M> findNotDelAll() {
        DetachedCriteria crit = DetachedCriteria.forClass(baseClass());
        crit.add(Restrictions.eq("del_flag", "0"));
        return baseDAO.find(crit);
    }

    public int findTotal(Map<String, String> map) {
        return baseDAO.findTotal(this.getcrit(map));
    }

    public List<M> find(Map<String, String> map) {
        DetachedCriteria crit = this.getcrit(map);
        crit.addOrder(Order.desc("update_date"));
        return baseDAO.find(crit);
    }

    public M findUnique(Map<String, String> map) {
        DetachedCriteria crit = this.getcrit(map);
        crit.add(Restrictions.eq("del_flag", "0"));
        crit.addOrder(Order.desc("update_date"));
        return baseDAO.findUnique(crit);
    }

    public List<M> find(int page_num, int per_page, Map<String, String> map) {
        DetachedCriteria crit = this.getcrit(map);
        crit.add(Restrictions.eq("del_flag", "0"));
        crit.addOrder(Order.desc("create_date"));
        return baseDAO.find(page_num, per_page, crit);
    }

    public PageResponse find(PageRequest pageRequest, Searchable searchable) {
        DetachedCriteria crit = this.getcrit(searchable.getMap());
        if (pageRequest.getOrder() == null || pageRequest.getSort() == null) {
            crit.addOrder(Order.desc("update_date"));
        } else {
            if (pageRequest.getSort().equals("desc")) {
                crit.addOrder(Order.desc(pageRequest.getOrder()));
            } else {
                crit.addOrder(Order.desc(pageRequest.getSort()));
            }
        }
        return baseDAO.find(pageRequest, crit);
    }

    public DetachedCriteria getcrit(Map<String, String> map) {
        DetachedCriteria crit = DetachedCriteria.forClass(baseClass());
        Map<String, DetachedCriteria> aliasMap = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String tempkey = entry.getKey();
            String key = tempkey.substring(0, tempkey.lastIndexOf("_"));
            SearchOperator operator = SearchOperator.valueOf(tempkey.substring(tempkey.lastIndexOf("_") + 1));
            String value = entry.getValue();
            if (operator == SearchOperator.like || operator == SearchOperator.notLike) {
                value = "%" + value + "%";
            }
            if (operator == SearchOperator.prefixLike || operator == SearchOperator.prefixNotLike) {
                value = value + "%";
            }

            if (operator == SearchOperator.suffixLike || operator == SearchOperator.suffixNotLike) {
                value = "%" + value;
            }
            if (key.indexOf(".") > 0) {
                String[] keys = key.split("\\.");
                for (int i = 0; i < keys.length - 1; i++) {
                    String keyName = keys[i];
                    if (!aliasMap.containsKey(keyName)) {
                        if (i == 0) {
                            DetachedCriteria alias = crit.createCriteria(keyName, JoinType.LEFT_OUTER_JOIN);
                            aliasMap.put(keyName, alias);
                        } else {
                            DetachedCriteria alias = aliasMap.get(keys[i - 1]).createCriteria(keyName);
                            aliasMap.put(keyName, alias);
                        }
                    }
                }
                aliasMap.get(keys[keys.length - 2]).add(Restrictions.sqlRestriction("{alias}." + keys[keys.length - 1] + " " + operator.getSymbol() + " ?", value, StringType.INSTANCE));
            } else {
                crit.add(Restrictions.sqlRestriction("{alias}." + key + " " + operator.getSymbol() + " ?", value, StringType.INSTANCE));
            }
        }
        return crit;
    }

    public M findById(Long id) {
        return baseDAO.load(baseClass(), id);
    }

    @Transactional
    public M save(M base) {
        if (base.getId() == null) {
            baseDAO.insert(base);
            return base;
        } else {
            M temp = baseDAO.load(baseClass(), base.getId());
            BeanUtils.copyProperties(base, temp);
            temp.setUpdateDate(new Date());
            baseDAO.update(temp);
            return temp;
        }
    }

    @Transactional
    public M insert(M base) {
        baseDAO.insert(base);
        return base;
    }

//    @Transactional
//    public M update(M base) {
//        M temp = baseDAO.load(baseClass(), base.getId());
//        BeanUtils.copyProperties(base, temp);
//        temp.setUpdateDate(new Date());
//        baseDAO.update(temp);
//        return temp;
//    }

    @Transactional
    public void del(Long id) {
        M baseEntity = baseDAO.load(baseClass(), id);
        baseDAO.delete(baseEntity);
    }

    @Transactional
    public void del_logic(Long id) {
        M baseEntity = baseDAO.load(baseClass(), id);
        baseEntity.setDelFlag(BaseEntity.DEL_FLAG_DELETE);
        baseDAO.update(baseEntity);
    }

    @Transactional
    public void del(String ids) {
        baseDAO.delete(baseClass(), ids);
    }

    @Transactional
    public void del_logic(String ids) {
        baseDAO.logic_delete(baseClass(), ids);
    }

    public List<M> findByNameAndValue(String name, String value) {
        DetachedCriteria crit = DetachedCriteria.forClass(baseClass());
        crit.add(Restrictions.eq(name, value));
        return baseDAO.find(crit);
    }

}
