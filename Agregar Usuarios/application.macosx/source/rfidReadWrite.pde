import processing.serial.*;
PImage bg;
int lf=10;
int value;
String myString = null;
String finalString = null;
String rfid = null;
Serial myPort;
int clk=0;
JSONObject json;
void setup() {
  size(600, 200);
  myPort = new Serial(this, Serial.list()[1], 9600);
  myPort.clear();
  myString = myPort.readStringUntil(lf);
  myString = null;
  userForm();
  bg = loadImage("http://landing.intego.com/rs/intego/images/x8trial-header-bg2.jpg");

}
void draw() {
  
  background(bg);
  
 //Datos puerto serial
  while (myPort.available()>0) {
    myString = myPort.readStringUntil(lf);
    if (myString!=null) {
      myString = trim(myString);
    }
  }

    if (myString !=null) {
      String []myStringAr = split(myString, " ");
       rfid = join(myStringAr, "");
       boolean chkFlag = checkUIDAlreadyExists(rfid);
    }
    
  myString = null;
}
boolean checkUIDAlreadyExists(String rfid) {
  boolean bool=false;;
  String param="method=checkUIDAlreadyExists&uid="+rfid;
  String url = "http://192.168.64.2/Atencion/callApi.php?"+param;
  String retMsg = null;
  try {
    String[] chkStrRespAr = loadStrings(url);
    
      retMsg = chkStrRespAr[0];
      if(retMsg.equals("0")){
        
        lbl_error_success_msg.setLabel("Esta tarjeta ya está registrada!");
        println("Tarjeta ya exsite!");
      
      }else if(retMsg.equals("1")){
        
        //boton UID
        bool=true;
        lbl_uid.setPosition(100, 130);
        lbl_uid.setLabel(rfid);
        c.add(btn_addUser);
        
      }else{
        
        lbl_error_success_msg.setLabel("UID invalido!");
      }
  }
  catch(Exception e) {
    println("Error inesperado");
  }
  delay(1000);
  return bool;
  
}
void addUser(){
  String param="method=addUser&uid="+rfid;
  param+="&name="+txt_name.getValue();
  String url = "http://192.168.64.2/Atencion/callApi.php?"+param;
  
  String[] chkStrRespAr = loadStrings(url);
  for(int i=0;i<chkStrRespAr.length;i++){
    println(chkStrRespAr[i]);
  }
  String retMsg = chkStrRespAr[0];
  println(retMsg);
  lbl_error_success_msg.setLabel(retMsg);
  
}
