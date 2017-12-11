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
package com.linkedin.android.testbutler.demo;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.linkedin.android.testbutler.TestButler;

import org.junit.Rule;
import org.junit.Test;

public class DisplayResizerTest {

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void disableAnimations() throws Exception {

        TestButler.setDisplaySize(600, 800);

        sleep(5000);

        TestButler.resetDisplaySize();

        sleep(1000);

        TestButler.setDisplaySize(800, 1280);

        sleep(5000);

        TestButler.setDisplayDensity(160);

        sleep(5000);

        TestButler.resetDisplaySize();
        TestButler.resetDisplayDensity();

        sleep(5000);
    }

    private static void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
