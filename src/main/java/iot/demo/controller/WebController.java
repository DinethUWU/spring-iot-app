package iot.demo.controller;

import iot.demo.config.RedisUtil;
import iot.demo.model.SensorData;
import iot.demo.util.Constants;
import iot.demo.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.util.Map;

@Controller
@RequestMapping("/")
public class WebController {

    @Autowired
    private RedisUtil util;

    private JedisPool jedisPool = null;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(
            @RequestBody SensorData sensorData) {
        jedisPool = util.getJedisPool();
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hmset(Constants.SENSOR_TYPE, DataMapper.getDHT11DataMap(sensorData));
        }
        return new ResponseEntity<SensorData>(HttpStatus.OK);
    }

    @RequestMapping(value ="/getData/{sensorId}", method = RequestMethod.GET)
    public @ResponseBody Map<String,String> findValue(@PathVariable String sensorId) {
       Map<String,String> retrieveMap=null;
        jedisPool = util.getJedisPool();
        try (Jedis jedis = jedisPool.getResource()) {
           retrieveMap = jedis.hgetAll(sensorId);
        }
        return retrieveMap;
    }
}
