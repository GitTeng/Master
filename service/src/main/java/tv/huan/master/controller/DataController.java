package tv.huan.master.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tv.huan.master.common.model.MyResponse;
import tv.huan.master.entity.ClientLog;
import tv.huan.master.service.elk.ElkClientLogService;
import tv.huan.master.service.mongo.MongoClientLogService;
import tv.huan.master.service.redis.RedisClientLogService;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2017/4/5 0005
 * Time: 10:10
 * To change this template use File | Settings | File Templates
 */
@RestController
@RequestMapping("/client")
public class DataController {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RedisClientLogService redisClientLogService;
    @Autowired
    MongoClientLogService mongoClientLogService;
    @Autowired
    ElkClientLogService elkClientLogService;

    @RequestMapping(value = "save", method = {RequestMethod.POST})
    public MyResponse<ClientLog> save(@RequestBody String data) throws Exception {
        MyResponse<ClientLog> response =new MyResponse<>();
        ClientLog clientLog = JSON.parseObject(data, ClientLog.class);
        clientLog.setId(clientLog.getDnum());
        redisClientLogService.add(clientLog.getId(), clientLog);
        mongoClientLogService.insert(clientLog);
        logger.info(JSON.toJSONString(clientLog));
        response.setData(clientLog);
        return response;
    }
}
