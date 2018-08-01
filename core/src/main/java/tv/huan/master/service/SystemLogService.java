package tv.huan.master.service;

import org.springframework.stereotype.Service;
import tv.huan.master.common.service.BaseService;
import tv.huan.master.entity.SystemLog;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2015/4/20
 * Time: 15:28
 * To change this template use File | Settings | File Templates
 */
@Service
public class SystemLogService extends BaseService<SystemLog> {
    @Override
    protected Class baseClass() {
        return SystemLog.class;
    }

//    @Override
//    public PageResponse find(PageRequest easyUiPageRequest, Searchable searchable) {
//        Map<String, String> map = searchable.getMap();
//        DetachedCriteria crit = DetachedCriteria.forClass(baseClass());
//        crit.createAlias("user", "u");
//        for (Map.Entry<String, String> entry : map.entrySet()) {
//            String tempkey = entry.getKey();
//            String key = tempkey.substring(0, tempkey.lastIndexOf("_"));
//            SearchOperator operator = SearchOperator.valueOf(tempkey.substring(tempkey.lastIndexOf("_") + 1));
//            String value = entry.getValue();
//            if (operator == SearchOperator.like || operator == SearchOperator.notLike) {
//                value = "%" + value + "%";
//            }
//            if (operator == SearchOperator.prefixLike || operator == SearchOperator.prefixNotLike) {
//                value = value + "%";
//            }
//
//            if (operator == SearchOperator.suffixLike || operator == SearchOperator.suffixNotLike) {
//                value = "%" + value;
//            }
//            if (key.equals("user")) {
//                crit.add(Restrictions.eq("u.loginName", value));
//            } else {
//                crit.add(Restrictions.sqlRestriction("{alias}." + key + " " + operator.getSymbol() + " ?", value, StringType.INSTANCE));
//            }
//        }
//        PageResponse response = baseDAO.find(easyUiPageRequest, crit);
//        List<SystemLog> systemLogList = new ArrayList<SystemLog>();
//        for (Object o : ((List) response.getRows())) {
//            Object[] objects = (Object[]) o;
//            SystemLog systemLog = (SystemLog) objects[1];
//            systemLogList.add(systemLog);
//        }
//        response.setRows(systemLogList);
//        return response;
//    }
}
