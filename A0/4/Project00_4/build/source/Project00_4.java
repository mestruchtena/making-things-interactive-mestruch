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

public class Project00_4 extends PApplet {

//Constant of the window size
final int WINDOW_WIDTH = 640;
final int WINDOW_HEIGHT = 480;

//Array containing the 3 circles with behaviors
Circle [] circles = new Circle[3];

//Set program
public void setup(){

  //set window
  
  //set frame rate
  frameRate(60);

  //Initialize three circles with different styles
  circles[0] = new Circle(new PVector(0,0), 10, 2.0f, color(250,0,0));
  circles[1] = new Circle(new PVector(0,WINDOW_HEIGHT), 5, 1.5f, color(0,0,255));
  circles[2] = new Circle(new PVector(WINDOW_WIDTH, WINDOW_HEIGHT), 20, 1.2f, color(0,250,0));
}

//Draw function
public void draw(){

  //Set background to white
  background(255);

  //Set circle to move until desired point
  //(It will not move anywhere else until gets to the final destination)
  circles[0].moveUntil( new PVector(mouseX, mouseY));
  //Draw third circle
  circles[0].draw();

  //Set second circle to follow the mouse
  circles[1].followTo( new PVector(mouseX, mouseY));
  //Draw second circle
  circles[1].draw();

  //initialize position to follow
  PVector positionToFollow;
  //Check what is closer, the mouse or the first circle and set position to follow
  if( dist(mouseX, mouseY, circles[2].getPosition().x, circles[2].getPosition().y)
      < dist(circles[0].getPosition().x, circles[0].getPosition().y, circles[2].getPosition().x, circles[2].getPosition().y)){
        positionToFollow = new PVector(mouseX, mouseY);
  }else{
    positionToFollow = circles[0].getPosition();
  }
  //Follow desired position
  circles[2].followTo( positionToFollow );
  //Draw third circle
  circles[2].draw();



}

//Custom class to draw and move a Circle
public class Circle{
  //Position of the Circle
  PVector mPosition;
  //Speed of the circle
  float mSpeed;
  //Color of the circle
  int mColor;
  //Diameter of the circle
  float mDiameter;
  //Final position to move
  PVector mFinalPositionToMove = null;

  //Constructor to initialize circle
  Circle(PVector initialPosition, float diameter, float speed, int c){
    //Save given parameters
    mPosition = initialPosition;
    mSpeed = speed;
    mColor = c;
    mDiameter = diameter;


  }

  //Get color
  public int getColor(){
    return mColor;
  }

  //Get current position
  public PVector getPosition(){
    return mPosition;
  }

  //Get current diameter
  public float getDiameter(){
    return mDiameter;
  }

  //Follow desired position
  public void followTo( PVector finalPosition){

    //Get default vector to compute angle
    PVector vector1 = new PVector(10, 0);
    PVector vector2 = new PVector(finalPosition.x - mPosition.x, finalPosition.y - mPosition.y);
    //Compute angle between the vectors
    float angle = PVector.angleBetween(vector1, vector2);
    //Fix angle for all the positions of the final position against the initial position
    if (vector2.y < 0){
      angle *= -1;
    }
    //Move position towards the final position with the computed angle
    mPosition.x += mSpeed * (float) cos(angle);
    mPosition.y += mSpeed * (float) sin(angle);

  }

  public void moveUntil( PVector finalPosition){

    //If we don't have a destintation, we set it up
    if (mFinalPositionToMove == null){
      mFinalPositionToMove = finalPosition;
    }
    //Get default vector to compute angle
    PVector vector1 = new PVector(10, 0);
    PVector vector2 = new PVector(mFinalPositionToMove.x - mPosition.x, mFinalPositionToMove.y - mPosition.y);
    //Compute angle between the vectors
    float angle = PVector.angleBetween(vector1, vector2);
    //Fix angle for all the positions of the final position against the initial position
    if (vector2.y < 0){
      angle *= -1;
    }
    //Move position towards the final position with the computed angle
    mPosition.x += mSpeed * (float) cos(angle);
    mPosition.y += mSpeed * (float) sin(angle);

    //If arrived to destination, we set final position to null
    if( dist(mPosition.x, mPosition.y, mFinalPositionToMove.x, mFinalPositionToMove.y) < mSpeed){
      mFinalPositionToMove = null;
    }
  }

  //Draw function
  public void draw(){
    //If we have a final point to follow, we draw a line until that point
    if(mFinalPositionToMove != null){
      stroke(0);
      line(mPosition.x, mPosition.y, mFinalPositionToMove.x, mFinalPositionToMove.y);
    }
    //Fill circle
    fill(mColor);
    noStroke();
    //Draw circle
    ellipse(mPosition.x, mPosition.y, mDiameter, mDiameter);


  }
}
  public void settings() {  size(640, 480); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Project00_4" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
