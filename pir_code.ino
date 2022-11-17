
#include<Adafruit_MQTT_Client.h>
#include<ESP8266WiFi.h>

#define wifi_name "YOUR WIFI NAME"
#define pwd "YOUR WIFI PASSWORD"
#define server "io.adafruit.com"
#define port 1883
#define username "ADAFRUIT USERNAME"
#define aiokey "ADAFRUIT KEY"


#define ledPin D0
#define pirPin D1

WiFiClient IoT;

Adafruit_MQTT_Client mqtt(&IoT, server,port,username,aiokey);
Adafruit_MQTT_Publish feed= Adafruit_MQTT_Publish(&mqtt, username"/feeds/pirdata");



int pirValue;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  delay(10);
  WiFi.begin(wifi_name,pwd);
  while(WiFi.status () != WL_CONNECTED)
  {
   
    Serial.print(".");
  }
  Serial.println(WiFi.localIP());
  while(mqtt.connect())
  {
    Serial.print(".");
  }
    pinMode(ledPin,OUTPUT);
    pinMode(pirPin,INPUT);
    digitalWrite(ledPin,LOW);
  
}


void loop(){
  pirValue=digitalRead(pirPin);
  digitalWrite(ledPin,pirValue);
  Serial.println(pirValue);
  int data=digitalRead(pirPin);
  if(mqtt.connected()){

  Serial.println("Sending ");
  Serial.println(data);
  if(feed.publish(data)){
    Serial.println("Success");
  }
  else{
    Serial.println("Trying");
    }
  }
  if(data==1){
    delay(4000);
  }


delay(2000);
}
