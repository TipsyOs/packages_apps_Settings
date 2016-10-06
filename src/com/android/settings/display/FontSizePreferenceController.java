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
import android.content.res.Resources;
import android.provider.Settings;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.accessibility.ToggleFontSizePreferenceFragment;
import com.android.settings.core.PreferenceController;

public class FontSizePreferenceController extends PreferenceController {

    private static final String KEY_FONT_SIZE = "font_size";

    public FontSizePreferenceController(Context context) {
        super(context);
    }

    @Override
    protected boolean isAvailable() {
        return true;
    }

    @Override
    protected String getPreferenceKey() {
        return KEY_FONT_SIZE;
    }

    @Override
    public void updateState(PreferenceScreen screen) {
        final Preference preference = screen.findPreference(KEY_FONT_SIZE);
        if (preference == null) {
            return;
        }
        final float currentScale = Settings.System.getFloat(mContext.getContentResolver(),
                Settings.System.FONT_SCALE, 1.0f);
        final Resources res = mContext.getResources();
        final String[] entries = res.getStringArray(R.array.entries_font_size);
        final String[] strEntryValues = res.getStringArray(R.array.entryvalues_font_size);
        final int index = ToggleFontSizePreferenceFragment.fontSizeValueToIndex(currentScale,
                strEntryValues);
        preference.setSummary(entries[index]);
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        return false;
    }
}
