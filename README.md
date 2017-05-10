Android Things First Device Project
=====================================

This project is based on the ["first device" project](https://developer.android.com/things/training/first-device) described in the Android Things developer documentation implemented on a Raspberry Pi 3 B. Building the peripheral hardware and the Android Things app software in this project are detailed in the project web page.

https://mjordan56.github.io/AndroidIoTFirstDevice/

Pre-requisites
--------------

- Raspberry Pi 3 Model B
- Android Things Developer Preview 3 OS
- Android Studio 2.2+
- Electronic Components
  * Breadboard
  * Jumper wires
  * LED
  * Tactile Push Switch
  * Resistors:
    * 1 - 470 ohm
    * 1 - 10K ohm
- Optional Additional Electronic Components
  * GPIO Breakout Board
  * Ribbon cable

Build and Install
=================

In Android Studio, click on the "Run" button.

If you prefer to run on the command line, type

```bash
./gradlew installDebug
adb shell am start com.example.androidthings.myproject/.MainActivity
```
