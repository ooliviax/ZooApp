package com.example.zooapp

import android.os.Parcel
import android.os.Parcelable

data class Animal(
    val id: Int,
    val description: Description,
    val details: Details,
    val img: String,
    val name: String,
)