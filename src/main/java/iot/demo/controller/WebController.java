package iot.demo.controller;


import iot.demo.model.SensorData;
import iot.demo.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class WebController {

    @Autowired
    private RedisRepository redisRepository;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(
            @RequestBody SensorData sensorData) {
        redisRepository.add(sensorData);
        return new ResponseEntity<SensorData>(HttpStatus.OK);
    }

    @RequestMapping("/getAllData")
    public @ResponseBody
    Map<String, String> findAll() {
        Map<Object, Object> aa = redisRepository.findSensorData();
        Map<String, String> map = new HashMap<String, String>();
        for(Map.Entry<Object, Object> entry : aa.entrySet()){
            String key = (String) entry.getKey();
            map.put(key, aa.get(key).toString());
        }
        return map;
    }
}
