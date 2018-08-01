package tv.huan.master.service.elk;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tv.huan.master.common.util.ConfigTools;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2017/2/9 0009
 * Time: 10:45
 * To change this template use File | Settings | File Templates
 */
public class ElasticsearchClientHelper {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    private Map<String, TransportClient> clientMap = new ConcurrentHashMap<String, TransportClient>();

    private String clusterName = "elasticsearch";

    private ElasticsearchClientHelper() {
        init();
        //TO-DO 添加你需要的client到helper
    }

    public static ElasticsearchClientHelper getInstance() {
        return ElasticsearchHolder.INSTANCE;
    }

    private static class ElasticsearchHolder {
        private static final ElasticsearchClientHelper INSTANCE = new ElasticsearchClientHelper();
    }

    /**
     * 初始化默认的client
     */
    private void init() {
        Settings setting = Settings.builder()
                .put("client.transport.sniff", true)
                .put("cluster.name", "elasticsearch").build();
        try {
            TransportClient client = new PreBuiltTransportClient(setting)
                    .addTransportAddress(new InetSocketTransportAddress(
                            InetAddress.getByName(ConfigTools.getValue("elasticsearch.host")),
                            Integer.parseInt(ConfigTools.getValue("elasticsearch.port"))));
            clientMap.put(setting.get("cluster.name"), client);
        } catch (UnknownHostException e) {
            logger.error(e.getMessage());
        }
    }


    public TransportClient getClient() {
        return getClient(clusterName);
    }

    public TransportClient getClient(String clusterName) {
        return clientMap.get(clusterName);
    }

}
