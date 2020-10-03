package com.buddy.revision.Entities

import android.graphics.drawable.ColorDrawable
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class RegistrationEntities(

    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    var fullname:String,
    var email:String,
    var password:String,
    var isLoggedInt: Int,
    var theme_color: String
)