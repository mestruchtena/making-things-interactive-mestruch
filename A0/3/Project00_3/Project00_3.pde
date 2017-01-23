//Constant of the window size
final int WINDOW_WIDTH = 640;
final int WINDOW_HEIGHT = 480;

//Diameter of the circle
int circleDiameter = 5;
//Speed of the circle
float circleSpeed = 1.5;
//Position of the circle
float circleX = 0;
float circleY = 0;

//Setup program
void setup(){
  //Set window size
  size(640, 480);
  //Set framerate
  frameRate(60);

}

//Draw funcion
void draw(){

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
PVector movePositionWithSpeed( PVector currentPosition, PVector finalPosition, float speed){

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
