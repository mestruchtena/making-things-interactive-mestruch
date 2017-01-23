
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
void setup(){

  //Set window size
  size(640, 480);

  //Initialize array of circles
  circlesMoving = new ArrayList<Circle>();
  //Create random circles that will move around
  for (int i = 0; i < TOTAL_CIRCLES_MOVING; i++){
    circlesMoving.add(new Circle(new PVector(random(WINDOW_WIDTH),random(WINDOW_HEIGHT)),
                                  10.0, random(2)-1, random(2)-1,
                                  color(random(255),random(255),random(255)) ));
  }
  //Initialize array that will contain circles that are exploding
  circlesExploding = new ArrayList<Circle>();

}

//Draw loop
void draw(){

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
void mouseClicked() {
  //We create a circle where we clicked
  Circle c = new Circle(new PVector(mouseX,mouseY),
                                  0.0, 0.0, 0.0,
                                  color(random(255),random(255),random(255)) );
  //We add the circle in the exploding array
  circlesExploding.add(c);

}
