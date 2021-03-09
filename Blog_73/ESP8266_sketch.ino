
#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>
#include <ESP8266SSDP.h>
#include <PubSubClient.h>


#ifndef STASSID
#define STASSID "XXXX" // 연결할 WiFi SSID
#define STAPSK  "XXXX" // 연결한 WiFi SSID에 대한 PW
#endif

char* ssid = STASSID;
char* password = STAPSK;

char* conn_ip = "000.000.0.0"; // Broker IP입력
int conn_port = 0000; // 개인 포트 입력
char* conn_topic = "TEST"; // 나중에 토픽은 In, Out용으로 구별해서 사용 가능

char* uuid = "0000-0000-0000-0000";

ESP8266WebServer HTTP(80);
WiFiClient espClient;
PubSubClient client(espClient);


void setUpMQTT(char* ip, int port, char* topic){
  conn_ip = ip;
  conn_port = port;
  conn_topic = topic;
}
void setUUIDs(char* uid){
  uuid = uid;
}
void startMQTT(){
  client.setServer(conn_ip, conn_port);
  client.setCallback(callback);
}
void callback(char* topic, byte* payload, unsigned int length) {
  Serial.print("Message arrived [");
  Serial.print(topic);
  Serial.print("] ");
  for (int i = 0; i < length; i++) {
    Serial.print((char)payload[i]);
  }
  Serial.println();

}
void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Attempt to connect
    if (client.connect("ESP8266Client")) {
      Serial.println("connected");
      // Once connected, publish an announcement...
      client.publish(conn_topic, "hello world");
      // ... and resubscribe
      client.subscribe(conn_topic);
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}
void sendMsg(char* msg){
  client.publish(conn_topic, msg);
}
void setUpWiFi(char* id, char* pw){
  ssid = id;
  password = pw;
  WiFi.mode(WIFI_STA);
}
void setUpSSDP(){
    SSDP.setSchemaURL("description.xml");
    SSDP.setHTTPPort(80);
    SSDP.setName("Philips hue clone");
    SSDP.setSerialNumber("001788102201");
    SSDP.setURL("index.html");
    SSDP.setModelName("Philips hue bridge 2012");
    SSDP.setModelNumber("929000226503");
    SSDP.setModelURL("http://www.meethue.com");
    SSDP.setManufacturer("Royal Philips Electronics");
    SSDP.setManufacturerURL("http://www.philips.com");
    SSDP.setUUID("000-000-000");
    
}
void startWiFi(){
    WiFi.begin(ssid, password);
    if (WiFi.waitForConnectResult() == WL_CONNECTED) {

    Serial.printf("Starting HTTP...\n");
    HTTP.on("/index.html", HTTP_GET, []() {
      HTTP.send(200, "text/plain", "Hello World!");
    });
    HTTP.on("/description.xml", HTTP_GET, []() {
      SSDP.schema(HTTP.client());
    });
    HTTP.begin();
    Serial.printf("Ready!\n");
    Serial.print(WiFi.localIP());
  } else {
    Serial.printf("WiFi Failed\n");
    while (1) {
      delay(100);
    }
  }  
}
void startSSDP(){
   SSDP.begin();
}

void setup() {
  Serial.begin(9600);
  Serial.println();
  Serial.println("Starting WiFi...");
  startWiFi();
  setUpSSDP();
  startSSDP();
  setUpMQTT("192.168.0.89", 1883, "TEST");
  startMQTT();
  
}


void loop() {
  
  if(Serial.available()>0){
    String Input = Serial.readString();
    Serial.print("DATA : ");
    Serial.println(Input);
    if(Input.startsWith("SET_WIFI")){
      Serial.println("SET_WIFI CMD RECEIVED");
      
    }
    if(Input.startsWith("SET_SSDP")){
      Serial.println("SET_SSDP CMD RECEIVED");
    }
    if(Input.startsWith("SEND_MSG")){
      int len = Input.length();
      String msg = Input.substring(9, len);
      char buff[len-8];
      msg.toCharArray(buff, len-8);
      Serial.println(buff);
      sendMsg(buff);
    }
    if(Input.startsWith("SET_UUID")){
      Serial.println("SET_UUID CMD RECEIVED");
    }
  }
  else{
    HTTP.handleClient();
    delay(1);
    if(!client.connected()){
      reconnect();
      
    }else{
      client.loop();
    }
  }
}
