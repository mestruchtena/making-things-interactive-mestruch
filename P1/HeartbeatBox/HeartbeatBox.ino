#include <QueueArray.h>
#include <SPI.h>
#include <SD.h>


//  Variables
int pulsePin = 0;                 // Pulse Sensor purple wire connected to analog pin 0
int blinkPin = 13;                // pin to blink led at each beat
int fadePin = 5;                  // pin to do fancy classy fading blink at each beat
int fadeRate = 0;                 // used to fade LED on with PWM on fadePin
int inPin = 7;                    // Pin for a pushbutton
const int motionSensorPin = 2;    // Pin for the motion sensor

int val = 0;                      // variable for reading the pin status of the button to activate the heartbeat sensor
int buttonPressed = 0;            // Aux varaible to keep if the button has been pressed.

// Volatile Variables, used in the interrupt service routine!
volatile int BPM;                   // int that holds raw Analog in 0. updated every 2mS
volatile int Signal;                // holds the incoming raw data
volatile int IBI = 600;             // int that holds the time interval between beats! Must be seeded!
volatile boolean Pulse = false;     // "True" when User's live heartbeat is detected. "False" when not a "live beat".
volatile boolean QS = false;        // becomes true when Arduoino finds a beat.

//Times between actions to determine what we will do
unsigned long start, finished, elapsed;
unsigned long startBetweenBeats, finishedBetweenBeats;
unsigned long startInBeats, finishedInBeats;

//Time the light will be on when detecting motion
const int TIME_LIGHT_ON = 10000;

//Array that will contain the time between beats
QueueArray <int> beatsQueue;

//Variables to keep track of the beat
int currentBeat = -1;
//Variable to know when to show next beat
int wait = 0;

void setup(){

  
  Serial.begin(115200);   //Start serial port

/* CODE TO WRITE TO SD CARD NOT WORKING
 * 
 * 
  File heartbeatFile;
  if (!SD.begin(10)) {
    Serial.println("initialization failed!");
    return;
  }
  
  heartbeatFile = SD.open("heartbeat.txt");
  
  if (heartbeatFile) {
    while (heartbeatFile.available()) {
      beatsQueue.push(heartbeatFile.read());
    }

     heartbeatFile.close();
  }else {
    // if the file didn't open, print an error:
    Serial.println("error 1 opening heartbeat.txt");
  }*/
  
  pinMode(inPin, INPUT);    // declare pushbutton as input
  
  pinMode(blinkPin,OUTPUT);         // pin that will blink to your heartbeat!
  pinMode(fadePin,OUTPUT);          // pin that will fade to your heartbeat!
  
  pinMode(motionSensorPin, INPUT);  //Pin that will read the motion sensor
  
  start = millis();                 //Initialize the start of the time
  interruptSetup();                 // sets up to read Pulse Sensor signal every 2mS
   
}


//  Where the Magic Happens
void loop(){

    
    val = digitalRead(inPin);  // read input value
    
    
    if (val == LOW) {         // check if the input is HIGH (button released)
      buttonPressed = 1;
    }else if(buttonPressed == 1){ //If the button is pressed 
      
      Serial.println(beatsQueue.count()); //Print how many beats we have colleceted
      if(beatsQueue.count() < 5){ //If we don't have enough beats we blink 3 times with a long first one
        Serial.println("NOT ENOUGH BEATS!!!!");
        digitalWrite(blinkPin,HIGH);
        delay(2000);
        digitalWrite(blinkPin,LOW);
        delay(200);
        digitalWrite(blinkPin,HIGH);
        delay(200);
        digitalWrite(blinkPin,LOW);
        delay(200);
        digitalWrite(blinkPin,HIGH);
        delay(200);
        digitalWrite(blinkPin,LOW);

        //Delete all the beats
        for (int i = 0; i < beatsQueue.count(); i++){
          beatsQueue.pop();
        }
        
      }else{ // We have collected enough, we can use them to show 
        
       /* CODE TO WRITE TO SD CARD NOT WORKING
       *
       *File heartbeatFile = SD.open("heartbeat.txt", FILE_WRITE);*/

        //Print to know what are the beats
        Serial.print("Beats are: ");
        for (int i = 0; i < beatsQueue.count(); i++){
          int beat = beatsQueue.pop();
          /*CODE TO WRITE TO SD CARD NOT WORKING
           * 
           * if(heartbeatFile){
            heartbeatFile.println(beat);
          }*/
          Serial.print(beat);
          Serial.print(", ");
          beatsQueue.push(beat);
        }

        /*CODE TO WRITE TO SD CARD NOT WORKING
         * 
         * if(heartbeatFile){
          heartbeatFile.close();
        }else {
          // if the file didn't open, print an error:
          Serial.println("error 2 opening heartbeat.txt");
        }
        */
        // Blink twice to know it was a success
        Serial.println(")");
        currentBeat = -1;
        digitalWrite(blinkPin,HIGH);
        delay(200);
        digitalWrite(blinkPin,LOW);
        delay(200);
        digitalWrite(blinkPin,HIGH);
        delay(200);
        digitalWrite(blinkPin,LOW);
      }
      //Set button back to 0
      buttonPressed = 0;
    }

    //If we have enough beats and we didn't press the button
    if (beatsQueue.count() == 5 && buttonPressed == 0){
      //We check the motion sensor
      int value= digitalRead(motionSensorPin);
      if (value == HIGH){
        //Update the sart of time to compute until when we show it
        start = millis();
      }

      //If we are still in time
      if(millis() - start < TIME_LIGHT_ON){

        //Check if we have to wait for the next beat
        if(wait < 0){
          //Update beat
          currentBeat++;
          if(currentBeat > 4){
            currentBeat = 0;
          }
          //Set beat as true
          QS = true;
          //Update the wait time with the new beat
          wait = beatsQueue.pop();
          beatsQueue.push(wait);

          //Print the information of the wait
          Serial.print("This is the wait ");
          Serial.println(wait);
          Serial.println(currentBeat);

          //Blink the LED
          digitalWrite(blinkPin,HIGH);
          delay(8);
          digitalWrite(blinkPin,LOW);
        }else{
          //Reduce the wait by 20
          wait -= 20;
        }  
      }else{
        wait = 0;
      }



    }

    
   
  
  if (QS == true){     // A Heartbeat Was Found
                       // BPM and IBI have been Determined
                       // Quantified Self "QS" true when arduino finds a heartbeat
        fadeRate = 255;         // Makes the LED Fade Effect Happen
                                // Set 'fadeRate' Variable to 255 to fade LED with pulse
       
        QS = false;                      // reset the Quantified Self flag for next time

  }
  
  ledFadeToBeat();                      // Makes the LED Fade Effect Happen
  delay(20);                           //  take a break
}





void ledFadeToBeat(){
    fadeRate -= 15;                         //  set LED fade value
    fadeRate = constrain(fadeRate,0,255);   //  keep LED fade value from going into negative numbers!
    analogWrite(fadePin,fadeRate);          //  fade LED
    
  }
