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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.example.gestureunlock.R
import com.example.gestureunlock.convertLongToDateString
import com.example.gestureunlock.data.File


@BindingAdapter("fileNameString")
fun TextView.setfileNameString(item: File?) {
    item?.let {
        text = item.fileName
        setTextColor(when(item.owner){
            "shared" -> resources.getColor(R.color.black)
            else -> resources.getColor(R.color.colorPrimary)
        })
    }
}

@BindingAdapter("fileItemView")
fun ConstraintLayout.setfileItemView(item: File?) {
    item?.let {
        setBackgroundColor(when(item.owner){
            "shared" -> resources.getColor(R.color.white)
            else -> resources.getColor(R.color.colorPrimaryLight)
        })
    }
}


@BindingAdapter("createdTimeString")
fun TextView.setCreatedTimeString(item: File?) {
    item?.let {
        text = convertLongToDateString(item.createdTimeMilli)
    }
}

@BindingAdapter("fileImage")
fun ImageView.setFileImage(item: File?) {
    item?.let {
        setImageResource( when( item.owner){
            "shared" -> R.drawable.shared
            else -> R.drawable.locked
        })
    }
}