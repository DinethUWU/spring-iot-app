package iot.demo.controller;

import iot.demo.config.RedisUtil;
import iot.demo.model.SensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.util.List;

@Controller
@RequestMapping("/")
public class WebController {

    @Autowired
    private RedisUtil util;

    private JedisPool jedisPool = null;

    @RequestMapping(value = "/getSensorData", method = RequestMethod.POST)
    public @ResponseBody List<String> findValue(@RequestBody SensorData sensorData,@RequestParam("reqData") int reqData) {
        List<String>  retrieveMap=null;
        jedisPool = util.getJedisPool();
        try (Jedis jedis = jedisPool.getResource()) {
           String key=getListKey(sensorData.getSensorType(),sensorData.getSensorLocation());
           retrieveMap = jedis.lrange(key, 0, reqData);
        }
        return retrieveMap;
    }

    private String getListKey(String sensorType,String location){
        return sensorType+":"+location;
    }
}
