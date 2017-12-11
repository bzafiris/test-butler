package com.linkedin.android.testbutler;

/**
 * Created by bzafiris on 11/12/2017.
 */

/**
 * Copyright (C) 2016 LinkedIn Corp.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.os.IBinder;
import android.util.Log;
import android.view.Display;

import java.lang.reflect.Method;

/**
 * A helper class for enabling/disabling Animations before/after running Espresso tests.
 * <p>
 * Google recommends that animations are disabled when Espresso tests are being run:
 * https://code.google.com/p/android-test-kit/wiki/Espresso#Getting_Started
 */
final class DisplayResizer {

    private static String TAG = DisplayResizer.class.getSimpleName();

    private Method setForcedDisplaySizeMethod;
    private Method clearForcedDisplaySizeMethod;
    private Method setForcedDisplayDensityForUserMethod;
    private Method clearForcedDisplayDensityForUserMethod;
    private Object windowManagerObject;

    DisplayResizer() {

        try {
            Class<?> windowManagerStubClazz = Class.forName("android.view.IWindowManager$Stub");
            Method asInterface = windowManagerStubClazz.getDeclaredMethod("asInterface", IBinder.class);

            Class<?> serviceManagerClazz = Class.forName("android.os.ServiceManager");
            Method getService = serviceManagerClazz.getDeclaredMethod("getService", String.class);

            Class<?> windowManagerClazz = Class.forName("android.view.IWindowManager");

            Method[] methods = windowManagerClazz.getMethods();
            for(Method m: methods){
                Log.d(TAG, m.getName());
            }

            setForcedDisplaySizeMethod = windowManagerClazz.getDeclaredMethod("setForcedDisplaySize", int.class, int. class, int.class);
            setForcedDisplayDensityForUserMethod = windowManagerClazz.getDeclaredMethod("setForcedDisplayDensityForUser", int.class, int. class, int.class);

            clearForcedDisplaySizeMethod = windowManagerClazz.getDeclaredMethod("clearForcedDisplaySize", int.class);
            clearForcedDisplayDensityForUserMethod = windowManagerClazz.getDeclaredMethod("clearForcedDisplayDensityForUser", int.class, int.class);

            IBinder windowManagerBinder = (IBinder) getService.invoke(null, "window");
            windowManagerObject = asInterface.invoke(null, windowManagerBinder);
        } catch (Exception e) {
            throw new RuntimeException("Failed to access animation methods", e);
        }
    }

    public boolean setForcedDisplayDensity(int density)  {
        try {
            setForcedDisplayDensityForUserMethod.invoke(windowManagerObject, new Object[]{Display.DEFAULT_DISPLAY, density, -2});
        } catch (Exception e){
            Log.e(TAG, "Failed to resize display", e);
            return false;
        }
        return true;
    }

    public boolean clearForcedDisplayDensity()  {
        try {
            clearForcedDisplayDensityForUserMethod.invoke(windowManagerObject, new Object[]{Display.DEFAULT_DISPLAY, -2});
        } catch (Exception e){
            Log.e(TAG, "Failed to clear display density", e);
            return false;
        }
        return true;
    }


    public boolean setForcedDisplaySize(int width, int height)  {
        try {
            setForcedDisplaySizeMethod.invoke(windowManagerObject, new Object[]{Display.DEFAULT_DISPLAY, width, height});
        } catch (Exception e){
            Log.e(TAG, "Failed to resize display", e);
            return false;
        }
        return true;
    }

    public boolean clearForcedDisplaySize()  {
        try {
            clearForcedDisplaySizeMethod.invoke(windowManagerObject, new Object[]{Display.DEFAULT_DISPLAY});
        } catch (Exception e){
            Log.e(TAG, "Failed to clear display size", e);
            return false;
        }
        return true;
    }
}

