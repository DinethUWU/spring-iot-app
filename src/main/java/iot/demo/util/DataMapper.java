package iot.demo.util;

import iot.demo.model.SensorData;

import java.util.HashMap;
import java.util.Map;

public class DataMapper {

    public static Map<String,String> getDHT11DataMap(SensorData sensorData){
        HashMap<String,String> dht11DataMap=new HashMap<>();
        dht11DataMap.put(Constants.SENSOR_LOCATION,sensorData.getSensorLocation());
        dht11DataMap.put(Constants.HUMIDITY_VALUE,sensorData.getHumidityValue());
        dht11DataMap.put(Constants.TEMPERATURE_VALUE,sensorData.getTemperatureValue());
        return dht11DataMap;
    }

}
