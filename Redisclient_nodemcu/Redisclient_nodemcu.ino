
//network SSID (name)
#define WIFI_SSID "Your SSID"
#define WIFI_PASS "your password"

//redis config
#define REDISHOST "host address of redis"
#define REDISPORT 6379

#include <ESP8266WiFi.h>
#include <DHT.h>
#include <NTPClient.h>
#include <WiFiUdp.h>

DHT dht;

#define NTP_OFFSET   60*60*5// In seconds
#define NTP_INTERVAL 60 * 1000    // In miliseconds
#define NTP_ADDRESS  "www.sltime.org"

WiFiClient redis;
WiFiUDP ntpUDP;
NTPClient timeClient(ntpUDP, NTP_ADDRESS, NTP_OFFSET,  NTP_INTERVAL);

String dayStamp;
String timeStamp;
String formattedTime;
String formattedDate;

float humidity;
float temperature;
String key;
String sensorData;

void setup() {
  Serial.begin(115200);
  
  Serial.println("Serial initialized.");
  Serial.print("Connecting to ");
  Serial.print(WIFI_SSID);
  WiFi.begin(WIFI_SSID, WIFI_PASS);
  
  while (WiFi.status() != WL_CONNECTED) {  //Wait for the WiFI connection completion 
    delay(500);
    Serial.println("Waiting for connection"); 
  }
  Serial.println("");
  Serial.print("WiFi (");
  Serial.print(WiFi.macAddress());
  Serial.print(") connected with IP ");
  Serial.println(WiFi.localIP());
  Serial.print("DNS0 : ");
  Serial.println(WiFi.dnsIP(0));
  Serial.print("DNS1 : ");
  Serial.println(WiFi.dnsIP(1));

  //set the DHT11 output datapin 
  dht.setup(D4);
  timeClient.begin();
  timeClient.setTimeOffset(3600); 
}

void loop() {
  timeClient.update();

  formattedTime = timeClient.getFormattedTime();
  formattedDate = timeClient.getFormattedDate();

  //Extract Time and Date
  int splitT = formattedDate.indexOf("T");
  dayStamp = formattedDate.substring(0, splitT);
  timeStamp = formattedDate.substring(splitT+1, formattedDate.length()-1);

    if (!redis.connected()) {
      Serial.print("Redis not connected, connecting...");
      if (!redis.connect(REDISHOST,REDISPORT)) {
        Serial.print  ("Redis connection failed...");
        Serial.println("Waiting for next read");
        return; 
      } else
        Serial.println("OK");
    }

    humidity = dht.getHumidity();/* Get humidity value */
    temperature = dht.getTemperature();
    key = "DHT11:AreaX1";
    sensorData= "DATE: "+dayStamp+" TIME:"+timeStamp+" humidity:"+String(humidity)+" temperature:"+String(temperature);
    
    Serial.print("Time Formatted : ");
    Serial.println(formattedTime);

   redis.print(
      String("*3\r\n")
      +"$5\r\n"+"LPUSH\r\n"
      +"$"+key.length()+"\r\n"+key+"\r\n"
      +"$"+sensorData.length()+"\r\n"+sensorData+"\r\n"
    );
    
  while (redis.available() != 0)
    Serial.print((char)redis.read());
    delay(10000);/* Delay of amount equal to sampling period */
}
