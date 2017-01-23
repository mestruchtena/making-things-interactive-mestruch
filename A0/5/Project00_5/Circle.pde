
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
  color mColor;
  //Diameter of the circle
  float mDiameter;
  //Countdown to animate explosion
  int mExplosionCountdown = 120;
  //Variable to know if circle is exploding
  boolean mExploding = false;

  //Constructor of the circle
  Circle(PVector initialPosition, float diameter, float speedX, float speedY, color c){

    //Set variables provided
    mPosition = initialPosition;
    mSpeedX = speedX;
    mSpeedY = speedY;
    mColor = c;
    mDiameter = diameter;

  }

  //Get the current color
  color getColor(){
    return mColor;
  }

  //Get the current position
  PVector getPosition(){
    return mPosition;
  }

  //Get the circle diameter
  float getDiameter(){
    return mDiameter;
  }

  //Set the variable if circle is exploding
  void setExploding( Boolean exploding){
    mExploding = exploding;
  }

  //Check if circle is exploding
  boolean isExploding(){
    return mExploding;
  }

  //Move circle inside a given rectangle
  void moveInside(int x, int y, int width, int height){

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
  void explode(){
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
  int getExplosionCountdown(){
    return mExplosionCountdown;
  }

  //Check if the circle intersects with a given circle
  boolean intersects(Circle circle){
    return dist(circle.getPosition().x,circle.getPosition().y, mPosition.x, mPosition.y)
              < (circle.getDiameter() + mDiameter)/2;
  }

  //Draw circle
  void draw(){
    fill(mColor, 125);
    noStroke();
    ellipse(mPosition.x, mPosition.y, mDiameter, mDiameter);
  }
}
