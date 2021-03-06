import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Project00_1 extends PApplet {

//Constant of the window size
final int WINDOW_WIDTH = 640;
final int WINDOW_HEIGHT = 480;
//Radius of the circle
int circleRadius = 50;

//Setup the program
public void setup(){

  //Create the window
  
  //Set background to white
  background(255);

  //Get mid point of the window
  int circleX = (WINDOW_WIDTH/2);
  int circleY = (WINDOW_HEIGHT/2);

  //Set fill color
  fill(0, 0, 200);
  //Set stroke color
  stroke(0, 200, 0);
  //Set stroke weight
  strokeWeight(3);
  //Draw circle
  ellipse(circleX, circleY, circleRadius * 2, circleRadius * 2);
}
  public void settings() {  size(640, 480); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Project00_1" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
