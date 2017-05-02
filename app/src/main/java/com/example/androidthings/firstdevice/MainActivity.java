package com.example.androidthings.firstdevice;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;

/**
 * Skeleton of the main Android Things activity. Implement your device's logic
 * in this class.
 *
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 *
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 */
public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String BUTTON_PIN_NAME = "BCM21";
    private static final String LED_PIN_NAME = "BCM6";
    private static final int INTERVAL_BETWEEN_BLINKS_MS = 250;

    private Handler mHandler = new Handler();

    private Gpio mLedGpio;

    private Gpio mButtonGpio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        PeripheralManagerService service = new PeripheralManagerService();
        if (service != null) {
            Log.d(TAG, "Available GPIO: " + service.getGpioList());
        }
        else {
            Log.d(TAG, "Unable to create PeripheralManagerService.");
        }

        setupButtonGpio(service);
        setupLedGpio(service);
	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");

        // Remove handler events on close.
        mHandler.removeCallbacks(mBlinkRunnable);

        // Close the button switch GPIO resource.
        if (mButtonGpio != null) {
            mButtonGpio.unregisterGpioCallback(mCallback);
            try {
                mButtonGpio.close();
            } catch (IOException e) {
                Log.e(TAG, "Error on button switch PeripheralIO API", e);
            }
        }

        // Close the LED GPIO resource.
        if (mLedGpio != null) {
            try {
                mLedGpio.close();
            } catch (IOException e) {
                Log.e(TAG, "Error on LED PeripheralIO API", e);
            }
        }
    }

    /**
     * Button switch event callback.
     */
    private GpioCallback mCallback = new GpioCallback() {
        @Override
        public boolean onGpioEdge(Gpio gpio) {
            Log.i(TAG, "GPIO changed, button pressed");

            if (running) {
                try {
                    mLedGpio.setValue(false);
                } catch (IOException e) {
                    Log.e(TAG, "Error on LED blink runnable", e);
                }
                mHandler.removeCallbacks(mBlinkRunnable);
                running = false;
            }
            else {
                mHandler.post(mBlinkRunnable);
                running = true;
            }

            // Give the GPIO signal time to settle.
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) { /* ignore any interrupted exceptions */ }

            // Return true to keep callback active.
            return true;
        }
    };

    /**
     * Initialize the GPIO port to be used for the button switch.
     *
     * @param service - Peripheral manager service to communicate with GPIO.
     */
    private void setupButtonGpio(PeripheralManagerService service) {
        try {
            mButtonGpio = service.openGpio(BUTTON_PIN_NAME);
            mButtonGpio.setDirection(Gpio.DIRECTION_IN);
            mButtonGpio.setEdgeTriggerType(Gpio.EDGE_FALLING);

            mButtonGpio.registerGpioCallback(mCallback);
        } catch (IOException e) {
            Log.e(TAG, "Error on setting up button", e);
        }
    }

    /**
     * Initialize the GPIO port to be used for the button switch.
     *
     * @param service - Peripheral manager service to communicate with GPIO.
     */
    private void setupLedGpio(PeripheralManagerService service) {
        try {
            mLedGpio = service.openGpio(LED_PIN_NAME);

            // Configure the GPIO port as an output.
            mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        } catch (IOException e) {
            Log.e(TAG, "Error on setting up LED GPIO port.", e);
        }
    }

    boolean running = false;

    /**
     * Thread to control blinking the LED.
     */
    private Runnable mBlinkRunnable = new Runnable() {
        @Override
        public void run() {
            // Exit if the GPIO is already closed
            if (mLedGpio == null) {
                return;
            }

            try {
                // Toggle the LED state.
                mLedGpio.setValue(!mLedGpio.getValue());

                // Schedule another event after delay.
                mHandler.postDelayed(mBlinkRunnable, INTERVAL_BETWEEN_BLINKS_MS);
            } catch (IOException e) {
                Log.e(TAG, "Error on LED PeripheralIO API", e);
            }
        }
    };
}
