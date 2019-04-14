import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.serial.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class rfidReadTest extends PApplet {


PImage bg;
int lf=10;
int value;
String myString = null;
Serial myPort;

Button on_button; 
int clk=0;
public void setup() {
  
  myPort = new Serial(this, Serial.list()[1], 9600);
  myPort.clear();
  myString = myPort.readStringUntil(lf);
  myString = null;
   bg = loadImage("https://www.iso.org/obp/graphics/grs/b9bfbd59-d461-4cff-a71b-fb58167740c4_200.png");

}
public void draw() {
  
    background(bg);
  //obtiene la info del puerto serial 
  while (myPort.available()>0) {
    myString = myPort.readStringUntil(lf);
    if (myString!=null) {
      myString = trim(myString);
    }
    smooth();
  }
  //si el string no es null
  if(clk<=0){
    if (myString !=null) {
      String []myStringAr = split(myString, " ");
      String finalString = "method=getRFIDAttendanceApi&uid="+join(myStringAr, "");
      validateUser(finalString);
    }
  }else{
    
  }
  myString = null;
  
}
public void mousePressed()
{
  if (on_button.MouseIsOver()) {
    
    print("Stop");
    if(clk==1)
    on_button.stopBtn();
    println(clk++);
  }
}
//Ve si el UID es valido
public void validateUser(String uid) {
  delay(1000);
  if (uid!=null) {
    uid = trim(uid);


    String url = "http://192.168.64.2/Atencion/callApi.php?"+uid;

    try {
      String[] lines = loadStrings(url);

      for (int i = 0; i < lines.length; i++) {
        println(lines[i]);
      }
    }
    catch(Exception e) {
      println("Error inesperado");
    }
  }
}
// the Button class
class Button {
  String label; // button label
  float x;      // top left corner x position
  float y;      // top left corner y position
  float w;      // width of button
  float h;      // height of button
  
  // constructor
  Button(String labelB, float xpos, float ypos, float widthB, float heightB) {
    label = labelB;
    x = xpos;
    y = ypos;
    w = widthB;
    h = heightB;
  }
  
  public void Draw() {
    //this.addUserBtn();
  }
  public void addUserBtn(){
    fill(218);
    stroke(141);
    rect(x, y, w, h, 10);
    textAlign(CENTER, CENTER);
    fill(0);
    text(label, x + (w / 2), y + (h / 2));
  }
  public void stopBtn(){
    fill(218);
    stroke(141);
    y=y+40;
    rect(x, y, w, h, 30);
    textAlign(CENTER, CENTER);
    fill(0);
    text("Stop Add UID", x + (w / 2), y + (h / 2));
  }
  public boolean MouseIsOver() {
    if (mouseX > x && mouseX < (x + w) && mouseY > y && mouseY < (y + h)) {
      return true;
    }
    return false;
  }
 
}
  public void settings() {  size(200, 200); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "rfidReadTest" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
