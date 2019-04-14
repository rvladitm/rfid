import processing.serial.*;
PImage bg;
int lf=10;
int value;
String myString = null;
Serial myPort;

Button on_button; 
int clk=0;
void setup() {
  size(200, 200);
  myPort = new Serial(this, Serial.list()[1], 9600);
  myPort.clear();
  myString = myPort.readStringUntil(lf);
  myString = null;
   bg = loadImage("https://www.iso.org/obp/graphics/grs/b9bfbd59-d461-4cff-a71b-fb58167740c4_200.png");

}
void draw() {
  
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
void mousePressed()
{
  if (on_button.MouseIsOver()) {
    // print some text to the console pane if the button is clicked
    print("Stop");
    if(clk==1)
    on_button.stopBtn();
    println(clk++);
  }
}
//Ve si el UID es valido
void validateUser(String uid) {
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
