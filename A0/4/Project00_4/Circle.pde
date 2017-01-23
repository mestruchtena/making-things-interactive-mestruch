
//Custom class to draw and move a Circle
public class Circle{
  //Position of the Circle
  PVector mPosition;
  //Speed of the circle
  float mSpeed;
  //Color of the circle
  color mColor;
  //Diameter of the circle
  float mDiameter;
  //Final position to move
  PVector mFinalPositionToMove = null;

  //Constructor to initialize circle
  Circle(PVector initialPosition, float diameter, float speed, color c){
    //Save given parameters
    mPosition = initialPosition;
    mSpeed = speed;
    mColor = c;
    mDiameter = diameter;


  }

  //Get color
  color getColor(){
    return mColor;
  }

  //Get current position
  PVector getPosition(){
    return mPosition;
  }

  //Get current diameter
  float getDiameter(){
    return mDiameter;
  }

  //Follow desired position
  void followTo( PVector finalPosition){

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

  void moveUntil( PVector finalPosition){

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
  void draw(){
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
