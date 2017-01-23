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

public class Project00_5 extends PApplet {


//Constanst with the size of the WINDOW_WIDTH
final int WINDOW_WIDTH = 640;
final int WINDOW_HEIGHT = 480;
//Constant with total of circles moving
final int TOTAL_CIRCLES_MOVING = 30;

//Array containing circles moving around
ArrayList<Circle> circlesMoving;
//Array containing circles that are exploding
ArrayList<Circle> circlesExploding;

//Setup window and initial circles
public void setup(){

  //Set window size
  

  //Initialize array of circles
  circlesMoving = new ArrayList<Circle>();
  //Create random circles that will move around
  for (int i = 0; i < TOTAL_CIRCLES_MOVING; i++){
    circlesMoving.add(new Circle(new PVector(random(WINDOW_WIDTH),random(WINDOW_HEIGHT)),
                                  10.0f, random(2)-1, random(2)-1,
                                  color(random(255),random(255),random(255)) ));
  }
  //Initialize array that will contain circles that are exploding
  circlesExploding = new ArrayList<Circle>();

}

//Draw loop
public void draw(){

  //Set background to white
  background(255);

  //Loop through the different circles that are moving
  for (int i = 0; i < circlesMoving.size(); i++){
    //Move circle inside the window
    circlesMoving.get(i).moveInside(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
    //draw the circle
    circlesMoving.get(i).draw();

    //Check if the circles intersects to any exploding circle
    for (int j = 0; j < circlesExploding.size(); j++){
      if(circlesMoving.get(i).intersects(circlesExploding.get(j))){
        //If it intersects to any explosion, we set it as exploding
        circlesMoving.get(i).setExploding(true);
      }
    }
  }

  //We remove all exploding circles from the moving circles and add them
  //to the exploding array
  for (int i = 0; i < circlesMoving.size(); i++){
    if(circlesMoving.get(i).isExploding()){
      circlesExploding.add(circlesMoving.get(i));
      circlesMoving.remove(i);
    }
  }


  //Loop through the exploding circles to animate the explosion
  for (int j = 0; j < circlesExploding.size(); j++){
    //Animate explosion
    circlesExploding.get(j).explode();
    //Draw the circle
    circlesExploding.get(j).draw();
    //check if the explosion finished, then removing it.
    if( circlesExploding.get(j).getExplosionCountdown() <= 0){
      circlesExploding.remove(j);
    }
  }

}

//Function to be called when the mouse is clicked
public void mouseClicked() {
  //We create a circle where we clicked
  Circle c = new Circle(new PVector(mouseX,mouseY),
                                  0.0f, 0.0f, 0.0f,
                                  color(random(255),random(255),random(255)) );
  //We add the circle in the exploding array
  circlesExploding.add(c);

}

//Custom class to draw and move a Circle
public class Circle{

  //constant of Max diamater of the circle when exploding
  final float MAX_DIAMETER = 100;
  //Position of the Circle
  PVector mPosition;
  //Speed on X and Y
  float mSpeedX;
  float mSpeedY;
  //Color of the circle
  int mColor;
  //Diameter of the circle
  float mDiameter;
  //Countdown to animate explosion
  int mExplosionCountdown = 120;
  //Variable to know if circle is exploding
  boolean mExploding = false;

  //Constructor of the circle
  Circle(PVector initialPosition, float diameter, float speedX, float speedY, int c){

    //Set variables provided
    mPosition = initialPosition;
    mSpeedX = speedX;
    mSpeedY = speedY;
    mColor = c;
    mDiameter = diameter;

  }

  //Get the current color
  public int getColor(){
    return mColor;
  }

  //Get the current position
  public PVector getPosition(){
    return mPosition;
  }

  //Get the circle diameter
  public float getDiameter(){
    return mDiameter;
  }

  //Set the variable if circle is exploding
  public void setExploding( Boolean exploding){
    mExploding = exploding;
  }

  //Check if circle is exploding
  public boolean isExploding(){
    return mExploding;
  }

  //Move circle inside a given rectangle
  public void moveInside(int x, int y, int width, int height){

    //Move circle with the speed
    mPosition.x += mSpeedX;
    mPosition.y += mSpeedY;

    //Check if we are at any of the limits to change the direction and bounce back
    if(mPosition.x < x || mPosition.x > width){
      mSpeedX*=-1;
    }
    if(mPosition.y < y || mPosition.y > height){
      mSpeedY*=-1;
    }
  }

  //Animate explosion of the circle
  public void explode(){
    //Increase the diameter of the circle the first frames until MAX_DIAMETER
    if(mExplosionCountdown > 60){
      mDiameter = min(MAX_DIAMETER, mDiameter + 2);
    }

    //Make circle disappear when animation is finishing
    if (mExplosionCountdown <= MAX_DIAMETER/4){
      mDiameter -= 4;
    }
    //Reduce explosion countdown.
    mExplosionCountdown--;
  }

  //Get the current countdown of the explosion
  public int getExplosionCountdown(){
    return mExplosionCountdown;
  }

  //Check if the circle intersects with a given circle
  public boolean intersects(Circle circle){
    return dist(circle.getPosition().x,circle.getPosition().y, mPosition.x, mPosition.y)
              < (circle.getDiameter() + mDiameter)/2;
  }

  //Draw circle
  public void draw(){
    fill(mColor, 125);
    noStroke();
    ellipse(mPosition.x, mPosition.y, mDiameter, mDiameter);
  }
}
  public void settings() {  size(640, 480); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Project00_5" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
