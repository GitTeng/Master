package tv.huan.master.service.redis;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tv.huan.master.entity.ClientLog;
import tv.huan.master.common.util.ConfigTools;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date:2015/4/2
 * Time: 11:35
 * To change this template use File | Settings | File Templates
 */
@Service(value = "redisClientLogService")
public class RedisClientLogService{
    Logger logger = Logger.getLogger(Logger.class.getName());
    @Autowired
    StringRedisTemplate redisTemplate;

    public void add(String key, ClientLog clientLog) throws Exception {
        try {
            redisTemplate.opsForValue().set(key, JSON.toJSONString(clientLog), Integer.parseInt(ConfigTools.getValue("redis.expire")), TimeUnit.DAYS);
        } catch (Exception e) {
            logger.error("add redis error " + key + "--" + clientLog.getId(), e);
            throw e;
        }

    }

    public ClientLog get(String key) {
        return JSON.parseObject(redisTemplate.opsForValue().get(key), ClientLog.class);
    }

    public void del(String key) {
        redisTemplate.delete(key);
    }

}
