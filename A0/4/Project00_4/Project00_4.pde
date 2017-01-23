//Constant of the window size
final int WINDOW_WIDTH = 640;
final int WINDOW_HEIGHT = 480;

//Array containing the 3 circles with behaviors
Circle [] circles = new Circle[3];

//Set program
void setup(){

  //set window
  size(640, 480);
  //set frame rate
  frameRate(60);

  //Initialize three circles with different styles
  circles[0] = new Circle(new PVector(0,0), 10, 2.0, color(250,0,0));
  circles[1] = new Circle(new PVector(0,WINDOW_HEIGHT), 5, 1.5, color(0,0,255));
  circles[2] = new Circle(new PVector(WINDOW_WIDTH, WINDOW_HEIGHT), 20, 1.2, color(0,250,0));
}

//Draw function
void draw(){

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
