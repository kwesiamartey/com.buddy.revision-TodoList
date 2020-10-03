package com.buddy.revision.Entities

import android.graphics.Color
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "itemEntity")
data class ItemsEntity (
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    var type:String,
    var title:String,
    var description:String,
    var date:String,
    var status:Int,
    var reminderr:Int,
):Parcelable