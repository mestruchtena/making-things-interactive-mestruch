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

public class Project00_3 extends PApplet {

//Constant of the window size
final int WINDOW_WIDTH = 640;
final int WINDOW_HEIGHT = 480;

//Diameter of the circle
int circleDiameter = 5;
//Speed of the circle
float circleSpeed = 1.5f;
//Position of the circle
float circleX = 0;
float circleY = 0;

//Setup program
public void setup(){
  //Set window size
  
  //Set framerate
  frameRate(60);

}

//Draw funcion
public void draw(){

  //Set background to white
  background(255);

  //Move circle to new position depending on speed and mouse position
  PVector newCirclePosition = movePositionWithSpeed( new PVector( circleX, circleY), new PVector(mouseX, mouseY), circleSpeed);

  //Change circle position to new position
  circleX = newCirclePosition.x;
  circleY = newCirclePosition.y;

  //Draw circle
  fill(0, 200, 0);
  noStroke();
  ellipse(circleX, circleY, circleDiameter, circleDiameter);

}

//Function to move a position with a given speed
public PVector movePositionWithSpeed( PVector currentPosition, PVector finalPosition, float speed){

  //Get default vector to compute angle
  PVector vector1 = new PVector(10, 0);
  PVector vector2 = new PVector(finalPosition.x - currentPosition.x, finalPosition.y - currentPosition.y);
  //Compute angle between the vectors
  float angle = PVector.angleBetween(vector1, vector2);

  //Fix angle for all the positions of the final position against the initial position
  if (vector2.y < 0){
    angle *= -1;
  }
  //Move position towards the final position with the computed angle
  currentPosition.x += speed * (float) cos(angle);
  currentPosition.y += speed * (float) sin(angle);
  //Return position
  return currentPosition;
}
  public void settings() {  size(640, 480); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Project00_3" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
