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

public class Project00_2 extends PApplet {

//Constant of the window size
final int WINDOW_WIDTH = 640;
final int WINDOW_HEIGHT = 320;

//Variable with the max diameter
float circleMaxDiameter;
//Increment to of the diameter of all the circles
float circleIncrement;
//Color increment between different circles
float colorIncrement;
//Total columns and rows of the grid
int totalColumns = 20;
int totalRows = 10;

//Set the program
public void setup(){

  //Create the window
  
  //Set background to white
  background(255);

  //We compute maximum Circle size for grid 20*10 = 200
  circleMaxDiameter = min(WINDOW_WIDTH/totalColumns, WINDOW_HEIGHT/totalRows);

  //Compute increment of each circle
  circleIncrement = (circleMaxDiameter - 1.0f)/(totalColumns*totalRows);

  //Compute increment of color value until 255
  colorIncrement = 255.0f/200.0f;

  //Initialize diameter and color
  float currentDiameter = 1.0f;
  float currentColor = 0;

  //Loop through the different rows and columns
  for ( int i = 0; i < totalRows; i++){
    for ( int j = 0; j < totalColumns; j++){
      //Get current position at the grid position
      float circleX = j*circleMaxDiameter + (circleMaxDiameter/2);
      float circleY = i*circleMaxDiameter + (circleMaxDiameter/2);
      //Set fill color to the desired one
      fill(0, 0, currentColor);
      noStroke();
      //Draw circle at desired position
      ellipse(circleX, circleY, currentDiameter, currentDiameter);
      //Increase diameter and color value
      currentDiameter += circleIncrement;
      currentColor += colorIncrement;
    }
  }

}
  public void settings() {  size(640, 320); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Project00_2" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
