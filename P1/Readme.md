
# Heartbeat Lamp Box

This is the first project for the Making Things Interactive class at Carnegie Mellon University. It contains the Arduino code to run the object, as well as the process images to create it.

If you have any questions you can email me *Marc Estruch Tena* at *mestruch[at]andrew.cmu.edu*.

## Description
This project creates a lamp that will be able to save the heartbeat of a given person and reproduce it when someone moves around the object.

This lamp should be capable of saving the heartbeat of that person into the SD card so this lamp can be given to someone containing a specific heartbeat.

This project aims to be an emotional reminder by showing the heartbeat of a person that might not be present at that time.

## About

This project aims to be a prototype as close as possible to a functional device. For this reason fast fabrication techniques where used to create the device and Arduino as the electronics part.

The following techniques were used for **fabrication**:
- **Laser cut**: To create a box with a one way mirror acrylic that would let heartbeat light be seen but not the electronics.
- **3D printing**: This was used to create a customized base that would fit the acrylic box on top and would contain the electronics with holes for external connections (AC power, SD card and needed sensors).

<br>
The following **electronic components** where used:
- **Arduino UNO**: Arduino board that contains the logic of the code and it is in charge of reading the sensors and creating any outputs.
- **Heart beat sensor**: *PulseSensor* was used as the sensor to detect the heartbeat.
- **Arduino Data Logging Shield**: Shield that uses an SD Card to save the heartbeat (*Currently not working*). Also, it contained a prototyping board which allowed to solder the different elements on it.
- **6 LED**: LEDs used to show the heartbeat and some light feedback when the heartbeat reading is finished. It uses 4 LEDs as the heartbeat and 2 LEDS for fading the heartbeat.
- **Motion sensor**: Regular motion sensor to detect movement around the object and activate the heartbeat light for 10 seconds.
- **Button**: Used to record the heartbeat of a person when pressed.

This repository contains the Arduino code that makes this device work. It is extended from the PulseSensor basic code on reading the pulse. Some unrelated code has been removed and the logic with the different LEDs and sensors has been added.


## How to use it
1. Plug the box by the USB cable or the AC adaptor.
2. Record your pulse by pressing the button. 5 pulses need to be detected consecutively to record the pulse.
3. If the record has been successful, the LED will blink twice fast. If it has not been successful, the LED's will blink once long and twice short.
4. Now that the heartbeat is saved. Every time the device detects some motion around, it will reproduce the heartbeat. 



## How it has been made

![alt tag](https://github.com/mestruchtena/making-things-interactive-mestruch/blob/master/P1/Process_images/process_gif.gif?raw=true)

## Future work
Some lines of future work are the following:

* Add internet connectivity to share a person's heartbeat remotely.
* Realtime heartbeat reading to show that person's activity at the same moment.
* Create different shapes that can be more interesting, other than a cube.
* Integrate the heartbeat idea to specific objects that remind you of that person.
