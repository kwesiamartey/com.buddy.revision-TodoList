package com.buddy.revision.Daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.buddy.revision.Entities.RegistrationEntities


@Dao
interface RegistrationDao {
    @Query("SELECT * FROM registrationentities")
    fun getUsers() : LiveData<List<RegistrationEntities>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(registrationEntities: RegistrationEntities)

    @Update
    suspend fun updateUser(registrationEntities: RegistrationEntities)

    @Query("UPDATE registrationentities SET isLoggedInt= :isloggin WHERE id =:id")
    suspend fun updateuIsLoggedIn(isloggin:Int, id:Int)

    @Query("UPDATE registrationentities SET theme_color= :theme WHERE id =:id")
    suspend fun updatetheme(theme:String, id:Int)
}