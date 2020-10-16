/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.gestureunlock.ui.home

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.gestureunlock.R
import com.example.gestureunlock.data.File


@BindingAdapter("sleepDurationFormatted")
fun TextView.setSleepDurationFormatted(item: File?) {
    item?.let {
        text = "Hello"
    }
}


@BindingAdapter("sleepQualityString")
fun TextView.setSleepQualityString(item: File?) {
    item?.let {
        text = "File"
    }
}

@BindingAdapter("sleepImage")
fun ImageView.setSleepImage(item: File?) {
    item?.let {
        setImageResource(R.drawable.ic_sleep_5)
    }
}