/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.android.settings.display;

import android.content.Context;
import android.os.SystemProperties;
import android.provider.Settings;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;

import com.android.settings.core.PreferenceController;

import static android.provider.Settings.Secure.CAMERA_GESTURE_DISABLED;

public class CameraGesturePreferenceController extends PreferenceController implements
        Preference.OnPreferenceChangeListener {

    private static final String KEY_CAMERA_GESTURE = "camera_gesture";

    public CameraGesturePreferenceController(Context context) {
        super(context);
    }

    @Override
    protected String getPreferenceKey() {
        return KEY_CAMERA_GESTURE;
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        return false;
    }

    @Override
    public void updateState(PreferenceScreen screen) {
        final SwitchPreference preference =
                (SwitchPreference) screen.findPreference(KEY_CAMERA_GESTURE);
        if (preference != null) {
            int value = Settings.Secure.getInt(mContext.getContentResolver(),
                    CAMERA_GESTURE_DISABLED, 0);
            preference.setChecked(value == 0);
        }
    }

    @Override
    protected boolean isAvailable() {
        boolean configSet = mContext.getResources().getInteger(
                com.android.internal.R.integer.config_cameraLaunchGestureSensorType) != -1;
        return configSet
                && !SystemProperties.getBoolean("gesture.disable_camera_launch", false);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        boolean value = (Boolean) newValue;
        Settings.Secure.putInt(mContext.getContentResolver(), CAMERA_GESTURE_DISABLED,
                value ? 0 : 1 /* Backwards because setting is for disabling */);
        return true;
    }
}
