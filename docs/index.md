# Overview

The Android Things First Device project is the prerequisite "hello world" project for noobs getting started with Android Things. The [Android Things documentation](https://developer.android.com/things) is really pretty good but I did run into a few things (no pun intended) that weren't exactly clear to me. My confusion with the information in the Android Things documentation could be just my naivete with building peripheral hardware component of the project. I'm not a total hardware neophyte but this is most certainly __not__ my area of expertise.

My intent in this document is to identify the issues I ran into while creating this project, the answers and solutions I discovered and some of the customizations I tried.

# Getting Started

As I already stated, the [Android Things documentation](https://developer.android.com/things), in my opinion, is pretty good. Anyone doing Android Things development should read this documentation first. Getting started with Android Things development basically begins with selecting a hardware platform and that is also where the Android Things documentation begins.

While the [Hardware 101](https://developer.android.com/things/hardware/hardware-101.html) section is a nice inclusion to the documentation, especially for a software developer with minimal hardware knowledge, I found this section a little premature. Selecting a development kit was my first priority. I didn't come back to this section until I was building the circuitry for this first project and had questions about the circuitry I was building. Developers with more electronics experience will undoubtedly find this information very basic but as a software developer I found the information very useful once I needed it.

All of the [hardware opinions](https://developer.android.com/things/hardware/developer-kits.html) listed in the Android Things documentation should be relatively equivalent for noobs just getting started with Android Things and Internet of Things (IoT). I chose the [Raspberry Pi 3 B](https://developer.android.com/things/hardware/raspberrypi.html) for my Android Things device because it's relatively inexpensive, it's ubiquitous within DIY and hobbiests communities and there's a lot of great information on all aspects of the Raspberry Pi on the internet.

One down-side to selecting the Raspberry Pi 3 is the inability of connecting to the Raspberry Pi via a USB connection. This isn't a problem as the Raspberry Pi has an Ethernet port and Wi-Fi but I like the idea of connecting [Android Debug Bridge](https://developer.android.com/studio/command-line/adb.html) directly to an Android device for development. This could be a non-issue and just a limitation of my knowledge about developing on the Raspberry Pi 3.

# Hardware Component

## Setting Up The Raspberry Pi 3

Setting up the Raspberry Pi 3 with the Android Things OS was straightforward and easy. Just follow the [step-by-step instructions](https://developer.android.com/things/hardware/raspberrypi.html) in the Android Things documentation for Raspberry Pi 3.

One thing that wasn't clear to me was the size of the micro SD card to use. The Raspberry Pi 3 kit I purchased was the Vilros Raspberry Pi 3 Complete Starter Kit. This kit includes a 16GB micro SD imaged with the Raspbian OS. I wanted to preserve the image on this card and get a separate micro SD card for the Android Things OS. But what size to get? The Android Things documentation says to use an 8GB or larger micro SD card. But what is the maximum size supported by the Raspberry Pi 3? After a little investigation I found the Raspberry Pi 3 is compatible with a micro SD cards up to a maximum size of 32GB.

After following all the steps in the "Flashing the Image" section of the Android Things documentation, you should see the Android Things welcome screen.

![Android Things Welcome Screen](assets/images/androidthings_welcome_screenshot.png)

If an Ethernet cable is connected to the Raspberry Pi 3 there will be a block of text at the bottom of the screen with Ethernet connection information. __The Raspberry Pi 3 needs to be connected to a network, either with an Ethernet cable or over Wi-Fi, for Android Studio or Android Debug Bridge to be able to communicate with the Raspberry Pi 3. To use a Wi-Fi connection the Raspberry Pi 3 needs to use an Ethernet cable connection in order to set-up the Wi-Fi service. Once the Wi-Fi service has been configured the Ethernet cable can be removed. Also, the Ethernet cable must be connected to the Raspberry Pi 3 prior to the Android Things OS booting.__ The OS doesn't see the network when the cable is plugged in after completing the boot process.

There is important IP address information on the Android Things welcome screen that is needed to use with the ADB connect command. At the bottom of the welcome screen is a block of text that begins with "Ethernet eth0 connected". The last line in this block of text is the IP address of the Raspberry Pi 3. This IP address is needed to connect Android Studio to the Raspberry Pi 3.

__NOTE:__ I missed the note in the Android Things documentation the first time around about the Raspberry Pi 3 broadcasting the hostname __Android.local__ over Multicast DNS. This is a nice feature so you don't have to memorize an IP address that could change. I tried it on my network and was able to connect to my Raspberry Pi 3. Cool!

Connecting the Raspberry Pi 3 to your network by cable or over Wi-Fi is optional for this project. Either connection method is fine but the Raspberry Pi 3 must be connected to your network.

You can also skip the "Serial debug console" and "Configuring the UART mode" sections for this project as they are not needed.

## The Peripheral Hardware

The next step for this project is building the peripheral hardware to extend the Raspberry Pi 3 into a target Android Things IoT device. The software in the Android Things First Device app will interact with this device. While the peripheral hardware is a __very__ simple circuit it was obvious to me I was going to need to do some research to come up to speed on how to build this circuit.

For someone such as myself that's primarily a software developer this next step was a bit of a small challenge. Over the years, I've customized many computers, my first computer was an Apple ][+ (which I still have for sentimental reasons) which I customized with several hardware add-ons, I built a Windows PC from components, I've configured and set-up NAS servers, network routers and extenders and done a number of varied tasks over the years to get the most out of a variety of electronic devices but building circuits is something I haven't done since college.

First, I needed to get the necessary electronic components to build the peripheral hardware. There are some great electronic parts suppliers for DIYers and hobbiests. Two suppliers I like are [adafruit](https://adafruit.com) and [sparkfun](https://sparkfun.com). There are many more great suppliers but these are the two I found and liked. Both have a ton of great information and tutorials. I especially like the tutorial videos on [sparkfun](https://sparkfun.com). I highly recommend these videos for anyone needing help with basic electronics concepts. Some of the videos can be a little simplistic but they are short, effective and kind of fun.

I ordered the [Project Kit for Android Things](https://adafruit.com/product/3227) from [adafruit](https://adafruit.com) to use for my beginning Android Things projects. While the kit had the components needed for this first project, I found that connecting the GPIO pins on the Raspberry Pi 3 to the breadboard was a bit cumbersome. Doing a little more research I found the solution many hobbiests use is a 40-pin Cobbler. I looked at several cobblers and liked the one included in the [CanaKit Raspberry Pi GPIO Breakout Board / Cobbler Bundle (40-Pin T-Shaped - Assembled)](https://www.amazon.com/gp/product/B011D06Y4G/ref=oh_aui_detailpage_o03_s00?ie=UTF8&psc=1) from Amazon.com. The kit from adafruit has a number of components for future projects while the CanaKit has the cobbler, a ribbon cable, and a couple of nice reference cards; a resistor color table and a Raspberry Pi GPIO header quick reference. This first project can be completed with either kit but I like having the combined components from the two kits.

### Required Electronic Components

The following electronic components are needed for the First Device project:

* Breadboard
* Jumper wires
* LED
* Tactile Push Switch
* Resistors:
   * 1 - 470 ohm
   * 1 - 10K ohm

Additional recommended parts:

* GPIO Breakout Board
* Ribbon cable

### Building the Hardware Peripheral

The peripheral hardware for the First Device, as defined in the [Connect the Hardware](https://developer.android.com/things/training/first-device/connect-hardware.html) section of the Android Things documentation, is two distinct ciruits; one circuit for a button switch to provide GPIO input and a second circuit for GPIO output to a LED. This wasn't immediately apparent to me. Once I understood there were two separate ciruits involved the peripheral hardware layout and the app code made sense.

The Android Things documentation shows the layout for a generic Android Things hardware platform connected to a breadboard with both the button and LED circuits. In the following sections I've broken down the two circuits individually as well as the final combined circuit configuration.

#### Button Circuit

The button circuit uses a tactile push switch and a 10K ohm resister. Here is the breadboard layout for the button circuit on a Raspberry Pi 3:

[![First Device Button Circuit](assets/images/FirstDevice_Button_small.png "Click to see larger image")](assets/images/FirstDevice_Button_large.png)

I didn't understand how the tactile push switch was being used in the circuit until I found the following diagram from the [Raspberry Pi Cookbook](http://razzpisampler.oreilly.com/ch07.html):

![First Device Button Circuit](assets/images/tactile_push_switch.png)

I was confused by the four leads on the switch and how they were used in the circuit. As you can see from the switch diagram, leads A and B are used in our button circuit while the C and D leads are left unused. Pushing the button closes the switch to complete the circuit between A and B. So, when the switch is open current is being directed to the GPIO input pin and when the switch is close the current finds the path of least resistance through the switch to ground.

You can either try the [Button Circuit App](#button-circuit-app) code with this circuit or wait to try out the circuit after adding the LED circuit to the breadboard.

#### LED Circuit

The LED circuit uses a LED and a 470 ohm resister. Here is the breadboard layout for the LED circuit on a Raspberry Pi 3:

[![First Device LED Circuit](assets/images/FirstDevice_LED_small.png "Click to see larger image")](assets/images/FirstDevice_LED_large.png)


#### Combined Button and LED Circuit

The two circuits, the button and LED circuits, are shown in the following diagram:

[![First Device Combined Circuit](assets/images/FirstDevice_Both_small.png "Click to see larger image")](assets/images/FirstDevice_Both_large.png)

This is the complete peripheral device for the First Device project. The following section will now create the Android Things app to communicate with this device through the appropriate Android Things APIs.

# Software Component

## Android Things First Device App

The [Interact with Peripherals](https://developer.android.com/things/training/first-device/peripherals.html) section of the Android Things documentation has examples of three activities for demonstrating how to communicate with the GPIO ports that interact with the electronic devices (the button switch and the LED) in first device peripheral. You can clone or download the [Simple PIO sample app](https://github.com/androidthings/sample-simplepio) project from GitHub to try out the device but I like to create my own project and add the code so I can get a feel for creating a project and playing with some of the parameters. The Simple PIO sample app project has two versions of the simplepio app; a blink activity version and a buttom activity version.

To explore developing my first Android Things app I decided to incrementally build my app to investigate instantiating the PeripheralManagerService, then adding code to handle button events, followed by adding code to blink the LED and finally using the button to control blinking the LED.

### First Device App <a id="first-device-app"></a>

The Android Things team has a GitHub organization for [Android Things](https://github.com/androidthings) repositories. I used the [new-project-template](https://github.com/androidthings/new-project-template) as the starting point for my First Device app.

After creating my new Android Things app by importing the project template I cleaned up the template code and added the code from the [List available peripherals](https://developer.android.com/things/training/first-device/peripherals.html) section to instantiate the PeripheralManagerService to test getting the list of available GPIO ports.

To run the app on the Raspberry Pi 3 synch to the [v0.1](https://github.com/mjordan56/AndroidIoTFirstDevice/tree/v0.1) release of the First Device app and turn on the Raspberry Pi 3. At this point the project can be run with or without the peripheral hardware connected to the GPIO ports.

Once the Android Things OS has fully booted, use [Android Debug Bridge](https://developer.android.com/studio/command-line/adb.html) \(ADB\) connect to the Raspberry Pi 3.

```
adb connect Android.local
connected to Android.local:5555
```
> Note: an IP address can also be used with the adb connect command
>
> ex. adb connect 192.168.1.199

Then run the app from Android Studio. The blank MainActivity will be displayed on the monitor display and the following lines will be output to the "logcat" tab of the Android Monitor window.

```
05-02 16:53:16.896 5462-5462/com.example.androidthings.firstdevice D/MainActivity: onCreate
05-02 16:53:16.902 5462-5462/com.example.androidthings.firstdevice D/MainActivity: Available GPIO: [BCM12, BCM13, BCM16, BCM17, BCM18, BCM19, BCM20, BCM21, BCM22, BCM23, BCM24, BCM25, BCM26, BCM27, BCM4, BCM5, BCM6]
```

#### Button Circuit App

The first modification I made to the First Device app was to added the code specified in the [Handle button events](https://developer.android.com/things/training/first-device/peripherals.html) section to communicate with the button switch in the peripheral hardware. The output from interaction with the button switch is in the GpioCallback routine; a message is sent to logcat indicating that the GPIO trigger event has occurred.

```
05-02 18:12:19.441 15020-15020/com.example.androidthings.firstdevice I/MainActivity: GPIO changed, button pressed
```

When I tested this code I noticed that sometimes multiple log messages were being output. To confirm my suspicion I added a button pressed counter and modified the log message to show the number of times the button had been pressed. Sure enough, a single button press occasionally generated multiple trigger events. My guess is that the state of the GPIO port hasn't fully settled to it's new state. To correct this issue I put the thread to sleep for 250ms to allow the GPIO port to complete the edge state transition before continuing. This seemed to resolve the issue.

The output from the app, after pushing the button switch several times, is:

 ```
05-02 21:21:55.337 15850-15850/com.example.androidthings.firstdevice D/MainActivity: onCreate
05-02 21:21:55.342 15850-15850/com.example.androidthings.firstdevice D/MainActivity: Available GPIO: [BCM12, BCM13, BCM16, BCM17, BCM18, BCM19, BCM20, BCM21, BCM22, BCM23, BCM24, BCM25, BCM26, BCM27, BCM4, BCM5, BCM6]
05-02 21:22:08.224 15850-15850/com.example.androidthings.firstdevice I/MainActivity: GPIO changed, button pressed 1 times.
05-02 21:22:09.184 15850-15850/com.example.androidthings.firstdevice I/MainActivity: GPIO changed, button pressed 2 times.
05-02 21:22:09.948 15850-15850/com.example.androidthings.firstdevice I/MainActivity: GPIO changed, button pressed 3 times.
05-02 21:22:10.978 15850-15850/com.example.androidthings.firstdevice I/MainActivity: GPIO changed, button pressed 4 times.
 ```

To run the app on the Raspberry Pi 3 synch to the [v0.2](https://github.com/mjordan56/AndroidIoTFirstDevice/tree/v0.2) release of the First Device app and turn on the Raspberry Pi 3. The button circuit peripheral hardware needs to be connected to the GPIO ports.

#### LED Circuit App

The next modification to the First Device app is to add the code specified in the [Blink an LED](https://developer.android.com/things/training/first-device/peripherals.html) section to control the LED in the peripheral hardware. The output from interaction with the LED is to turn the LED on and off via a GPIO port. This is a really simple block of code that a runnable task that is started when the app is created and starts blinking the LED until the execution of the app is terminated.

To run the app on the Raspberry Pi 3 synch to the [v0.3](https://github.com/mjordan56/AndroidIoTFirstDevice/tree/v0.3) release of the First Device app and turn on the Raspberry Pi 3. The LED circuit peripheral hardware needs to be connected to the GPIO ports.

#### Combined Button and LED Circuit App

The next logical step for the First Device app is to have the button switch control turning the LED on and off. This version of the app takes the elements learned in this exercise and use them to create an Android Things app that has two integrated peripheral elements to make a single peripheral device.

To run the app on the Raspberry Pi 3 synch to the [v0.4](https://github.com/mjordan56/AndroidIoTFirstDevice/tree/v0.4) release of the First Device app and turn on the Raspberry Pi 3. Use the button to turn on and off the blinking state of the LED.

# Conclusion

This was an interesting first project for learning Android Things. There are a number of cool elements in the Android Things OS and associated API that make developing Internet of Things apps __much__ easier than a number of other alternatives. I look forward to continuing exploring the capabilities of Android Things!
