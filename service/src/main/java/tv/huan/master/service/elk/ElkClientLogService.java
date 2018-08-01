package tv.huan.master.service.elk;


import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;
import tv.huan.master.common.model.PageResponse;
import tv.huan.master.common.model.PageRequest;
import tv.huan.master.common.model.Searchable;
import tv.huan.master.common.util.TimeUtil;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2015/4/10
 * Time: 17:20
 * To change this template use File | Settings | File Templates
 */
@Service(value = "elkClientLogService")
public class ElkClientLogService {

    public PageResponse find(PageRequest request, Searchable searchable) {
        //        Criteria criteria = new Criteria();
//        criteria.and(Criteria.where("client.dnum").is("114493791"));
//        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria,new PageRequest(easyUiPageRequest.getPage(),easyUiPageRequest.getRows()));
//        Page page =elasticsearchTemplate.queryForPage(criteriaQuery, ClientLog.class);

        Client client = ElasticsearchClientHelper.getInstance().getClient();
        SearchResponse searchResponse = client.prepareSearch("acs_client*")// 检索的目录
                .addSort("timestamp", SortOrder.DESC)
                .setSearchType(SearchType.DEFAULT)// Query type
                .setQuery(getQueryBuilder(searchable.getMap()))
                .setFrom(request.getFirstResult()).setSize(request.getPageSize()).setExplain(true)
                .execute().actionGet();
        SearchHits hits = searchResponse.getHits();
        List<Map> mapList = new ArrayList<Map>();
        if (hits.getTotalHits() > 0) {
            SearchHit[] searchHists = hits.getHits();
            if (searchHists.length > 0) {
                for (SearchHit hit : searchHists) {
                    Map clientMap = (Map) hit.getSource().get("client");
                    clientMap.put("createTime", hit.getSource().get("timestamp"));
                    mapList.add(clientMap);
                }
            }
        }
        PageResponse response = new PageResponse();
        response.setTotal((int) hits.getTotalHits());
        response.setRows(mapList);
        return response;
    }

    public BoolQueryBuilder getQueryBuilder(Map<String, String> map) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        List<QueryBuilder> filterList = queryBuilder.filter();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String tempkey = entry.getKey();
            String key = tempkey.substring(0, tempkey.lastIndexOf("_"));
            String operator = tempkey.substring(tempkey.lastIndexOf("_") + 1);
            String value = entry.getValue();
            switch (operator) {
                case "eq":
                    filterList.add(QueryBuilders.commonTermsQuery("client." + key, value));
                    break;
                case "like":
                    filterList.add(QueryBuilders.prefixQuery("client." + key, value));
                    break;
                case "gt":
                    filterList.add(QueryBuilders.rangeQuery("client." + key).gt(value));
                    break;
                case "gte":
                    filterList.add(QueryBuilders.rangeQuery("client." + key).gte(value));
                    break;
                case "lt":
                    filterList.add(QueryBuilders.rangeQuery("client." + key).lt(value));
                    break;
                case "lte":
                    filterList.add(QueryBuilders.rangeQuery("client." + key).lte(value));
                    break;
            }
        }
        return queryBuilder;
    }

    /**
     * 基本统计
     *
     * @param startDate
     * @param endDate
     * @param map
     * @return
     */
    public double basic(String startDate, String endDate, Map<String, String> map) {
        long start = TimeUtil.controlTimeHHMMSS(startDate + " 00:00:00").getTime();
        long end = TimeUtil.controlTimeHHMMSS(endDate + " 23:59:59").getTime();
        String fq = "timestamp:" +
                " [ " +
                start +
                " TO  " +
                end +
                " } ";
        BoolQueryBuilder queryBuilder = getQueryBuilder(map);
        queryBuilder.filter().add(QueryBuilders.queryStringQuery(fq));
        SumAggregationBuilder sumAggregationBuilder = AggregationBuilders.sum("sum").field("client.count");
        Client client = ElasticsearchClientHelper.getInstance().getClient();
        SearchRequestBuilder requestBuilder = client.prepareSearch("acs_client*")// 检索的目录
                .setSearchType(SearchType.DEFAULT)// Query type
                .setQuery(queryBuilder)
                .addAggregation(sumAggregationBuilder)
                .setFrom(0).setSize(0).setExplain(false);
        SearchResponse response = requestBuilder.execute().actionGet();
        InternalSum agg = response.getAggregations().get("sum");
        return agg.getValue();
    }

    /**
     * 查找同一机型和软件版本当天是否有相同的errorcode上报
     */
    public String findSameLogUrl(String type, String ver, String errorCode) {
        Map<String, String> map = new HashMap<>();
        map.put("type_eq", type);
        map.put("ver_eq", ver);
        map.put("createDate_eq", TimeUtil.getYYYY_MM_DD(new Date()));
        map.put("module_eq", errorCode.substring(2, 6));
        map.put("code_eq", errorCode.substring(6));

        try {
            Client client = ElasticsearchClientHelper.getInstance().getClient();
            SearchResponse searchResponse = client.prepareSearch("acs_client" + TimeUtil.getYYYYMM(new Date()))// 检索的目录
                    .setSearchType(SearchType.DEFAULT)// Query type
                    .setQuery(getQueryBuilder(map))
                    .setFrom(0).setSize(1).setExplain(true)
                    .execute().actionGet();
            SearchHits hits = searchResponse.getHits();
            if (hits.getTotalHits() > 0) {
                Map clientMap = (Map) hits.getAt(0).getSource().get("client");
                Object url = clientMap.get("url");
                if (url != null) {
                    return url.toString();
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
