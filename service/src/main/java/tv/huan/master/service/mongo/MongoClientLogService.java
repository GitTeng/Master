package tv.huan.master.service.mongo;

import com.mongodb.BasicDBList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import tv.huan.master.common.model.PageResponse;
import tv.huan.master.common.model.PageRequest;
import tv.huan.master.common.model.Searchable;
import tv.huan.master.entity.ClientLog;
import tv.huan.master.common.util.TimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2015/4/10
 * Time: 17:20
 * To change this template use File | Settings | File Templates
 */
@Service(value = "mongoClientLogService")
public class MongoClientLogService {
    @Autowired
    private MongoTemplate mongo;

    public void insert(ClientLog clientLog) {
        mongo.insert(clientLog);
    }

    public void insertAll(List<ClientLog> list) {
        mongo.insertAll(list);
    }

    public PageResponse find(PageRequest request, Searchable searchable) {
        Query query = new Query(getCriteria(searchable.getMap()));
        query.with(new Sort(Sort.Direction.DESC, "createTime"));
        query.skip(request.getFirstResult());
        query.limit(request.getPageSize());
        List<ClientLog> list = mongo.find(query, ClientLog.class);
        PageResponse response = new PageResponse();
        response.setTotal((int) mongo.count(query, ClientLog.class));
        response.setRows(list);
        return response;
    }

    /**
     * 根据统计维度整理数据
     *
     * @param keys
     * @return
     */
    public List statisticLog(String date, String... keys) {
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("createDate").is(date));
        GroupBy groupBy = GroupBy.key(keys).initialDocument("{count:0}")
                .reduceFunction("function(obj, prev){prev.count+=obj.count}");
        GroupByResults<ClientLog> gb = mongo.group(criteria, "ClientLog", groupBy, ClientLog.class);
        return (BasicDBList) gb.getRawResults().get("retval");
    }

    /**
     * 查找同一机型和软件版本当天是否有相同的errorcode上报
     */
    public ClientLog findSameLog(String type, String ver, String errorCode) {
        Criteria criatira = new Criteria();
        criatira.andOperator(
                Criteria.where("type").is(type),
                Criteria.where("ver").is(ver),
                Criteria.where("createDate").is(TimeUtil.getYYYY_MM_DD(new Date())),
                Criteria.where("module").is(errorCode.substring(2, 6)),
                Criteria.where("code").is(errorCode.substring(6))
        );
        return mongo.findOne(new Query(criatira), ClientLog.class);
    }


    public BasicDBList groupByField(String startDate, String endDate, String fieldName, Map<String, String> map) {
        List<Criteria> list = getCriteriaList(map);
        long start = TimeUtil.controlTimeHHMMSS(startDate + " 00:00:00").getTime() / 1000;
        long end = TimeUtil.controlTimeHHMMSS(endDate + " 23:59:59").getTime() / 1000;
        list.add(Criteria.where("createTime").gte(start).lte(end));
        GroupBy groupBy = GroupBy.key(fieldName).initialDocument("{count:0}")
                .reduceFunction("function(doc, prev){prev.count+=obj.count}");
        GroupByResults<ClientLog> gb = mongo.group(new Criteria().andOperator(list.toArray(new Criteria[list.size()])), "ClientLog", groupBy, ClientLog.class);
        return (BasicDBList) gb.getRawResults().get("retval");
    }


    public Criteria getCriteria(Map<String, String> map) {
        if (map.size() == 0) {
            return new Criteria();
        } else {
            List<Criteria> list = getCriteriaList(map);
            return new Criteria().andOperator(list.toArray(new Criteria[list.size()]));
        }
    }

    public List<Criteria> getCriteriaList(Map<String, String> map) {
        List<Criteria> list = new ArrayList<Criteria>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String tempkey = entry.getKey();
            String key = tempkey.substring(0, tempkey.lastIndexOf("_"));
            String operator = tempkey.substring(tempkey.lastIndexOf("_") + 1);
            Object value = entry.getValue();
            if (operator.equals("eq")) {
                list.add(Criteria.where(key).is(value));
            } else if (operator.equals("like")) {
                list.add(Criteria.where(key).regex(value.toString()));
            } else if (operator.equals("ne")) {
                list.add(Criteria.where(key).ne(value));
            } else if (operator.equals("gt")) {
                list.add(Criteria.where(key).gt(value));
            } else if (operator.equals("gte")) {
                list.add(Criteria.where(key).gte(value));
            } else if (operator.equals("lt")) {
                list.add(Criteria.where(key).lt(value));
            } else if (operator.equals("lte")) {
                list.add(Criteria.where(key).lte(value));
            } else if (operator.equals("in")) {
                list.add(Criteria.where(key).in(value));
            } else if (operator.equals("notIn")) {
                list.add(Criteria.where(key).nin(value));
            }
        }
        return list;
    }

}
