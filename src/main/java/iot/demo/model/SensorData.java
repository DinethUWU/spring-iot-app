package iot.demo.model;

import java.io.Serializable;


public class SensorData implements Serializable {

    private String sensorType;

    private String sensorValue;

    private String timeStamp;

    public SensorData(){

    }

    public SensorData(String sensor1Value, String sensorValue, String timeStamp){
        this.sensorType=sensor1Value;
        this.sensorValue=sensorValue;
        this.timeStamp=timeStamp;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public String getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(String sensorValue) {
        this.sensorValue = sensorValue;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
