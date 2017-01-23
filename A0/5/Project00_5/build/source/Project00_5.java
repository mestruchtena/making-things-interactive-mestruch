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
public class Circle{
  
  final float MAX_DIAMETER = 100;
  PVector mPosition;
  float mSpeedX;
  float mSpeedY;
  int mColor;
  float mDiameter;
  
  int mExplosionCountdown = 120;
  boolean mExploding = false;
  
  
  Circle(PVector initialPosition, float diameter, float speedX, float speedY, int c){
    
    mPosition = initialPosition;
    mSpeedX = speedX;
    mSpeedY = speedY;
    mColor = c;
    mDiameter = diameter;
    
    
  }
  
  public int getColor(){
    return mColor;
  }
  
  public PVector getPosition(){
    return mPosition;
  }
  
  public float getDiameter(){
    return mDiameter;
  }
  
  public void setExploding( Boolean exploding){
    mExploding = exploding;
  }
  public boolean isExploding(){
    return mExploding;
  }
  
  public void moveInside(int x, int y, int width, int height){
    
    mPosition.x += mSpeedX;
    mPosition.y += mSpeedY;
    
    if(mPosition.x < x || mPosition.x > width){
      mSpeedX*=-1;
    }
    
    if(mPosition.y < y || mPosition.y > height){
      mSpeedY*=-1;
    }
  }
  
  public void explode(){
    if(mExplosionCountdown > 60){
      mDiameter = min(MAX_DIAMETER, mDiameter + 2);
    } 
    
    if (mExplosionCountdown <= MAX_DIAMETER/4){
      mDiameter -= 4;
    }
    
    mExplosionCountdown--;
  }
  
  public int getExplosionCountdown(){
    return mExplosionCountdown;
  }
  
  public boolean intersects(Circle circle){
    return dist(circle.getPosition().x,circle.getPosition().y, mPosition.x, mPosition.y) 
              < (circle.getDiameter() + mDiameter)/2;
  }
  
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
