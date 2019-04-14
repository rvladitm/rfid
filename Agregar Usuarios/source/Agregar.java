import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.serial.*; 
import interfascia.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class rfidReadWrite extends PApplet {


PImage bg;
int lf=10;
int value;
String myString = null;
String finalString = null;
String rfid = null;
Serial myPort;
int clk=0;
JSONObject json;
public void setup() {
  
  myPort = new Serial(this, Serial.list()[1], 9600);
  myPort.clear();
  myString = myPort.readStringUntil(lf);
  myString = null;
  userForm();
  bg = loadImage("http://landing.intego.com/rs/intego/images/x8trial-header-bg2.jpg");

}
public void draw() {
  
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
public boolean checkUIDAlreadyExists(String rfid) {
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
public void addUser(){
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


GUIController c;
IFTextField txt_name;
IFLabel lbl_name, lbl_uid, lbl_uid_msg, lbl_error_success_msg, lbl_heading, lbl_uid_txt;
IFButton btn_addUser, btn_getUID;
public void userForm() {
  //size(200, 100);
  background(150);

  c = new GUIController(this);
  lbl_heading = new IFLabel("Usuario UID - Agregar Usuario", 230, 20, 150);

  lbl_error_success_msg = new IFLabel("", 140, 40);

  lbl_name = new IFLabel("Ingrese Nombre: ", 30, 90);
  txt_name = new IFTextField("txt_name", 120, 86, 200);

  lbl_uid = new IFLabel("UID", 30, 130);
  lbl_uid_txt = new IFLabel("", 120, 130);

  lbl_uid_msg = new IFLabel("* Aproxime la tarjeta en el lector \n luego presione obtener UID", 350, 80);

  btn_addUser = new IFButton("Añadir usuario", 400, 130, 80, 20);
  btn_getUID = new IFButton("obtener Uid", 300, 190, 80);

  c.add(lbl_heading);
  c.add(lbl_error_success_msg);

  c.add(lbl_name);
  c.add(txt_name);

  c.add(lbl_uid);
  c.add(lbl_uid_txt);
  c.add(lbl_uid_msg);


  //c.add(btn_addUser);
  //c.add(btn_getUID);
  btn_getUID.setSize(60, 20);

  btn_addUser.addActionListener(this);
  btn_getUID.addActionListener(this);
}

public void actionPerformed (GUIEvent e) {
  //if (e.getSource() == btn_getUID) {
    
  //} else 
  if (e.getSource() == btn_addUser) {
    addUser();
    
    
  }
}
  public void settings() {  size(600, 200); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "rfidReadWrite" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
