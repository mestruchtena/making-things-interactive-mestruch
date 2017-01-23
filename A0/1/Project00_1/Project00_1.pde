//Constant of the window size
final int WINDOW_WIDTH = 640;
final int WINDOW_HEIGHT = 480;
//Radius of the circle
int circleRadius = 50;

//Setup the program
void setup(){

  //Create the window
  size(640, 480);
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
