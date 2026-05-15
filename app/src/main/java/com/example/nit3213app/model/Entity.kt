package com.example.nit3213app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Entity(
    val properties: HashMap<String, String>
) : Parcelable