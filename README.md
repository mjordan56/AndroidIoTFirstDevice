Android Things First Device Project
=====================================

This project is based on the "first device" project described in the Android Things developer documentation implemented on a Raspberry Pi 3 B.

https://developer.android.com/things/training/first-device


Pre-requisites
--------------

- Raspberry Pi 3 Model B
- Android Things Developer Preview 3 OS
- Android Studio 2.2+


Build and Install
=================

In Android Studio, click on the "Run" button.

If you prefer to run on the command line, type

```bash
./gradlew installDebug
adb shell am start com.example.androidthings.myproject/.MainActivity
```
