package com.buddy.revision.Daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.buddy.revision.Entities.ItemsEntity


@Dao
interface ItemDao {

    @Query("SELECT * FROM itemEntity WHERE type =:type ORDER BY id DESC")
    fun getItems(type:String) : LiveData<List<ItemsEntity>>

    @Query("SELECT * FROM itemEntity WHERE title LIKE (:word)")
    fun search_item(word:String) : LiveData<List<ItemsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(itemsEntity: ItemsEntity)

    @Query("UPDATE itemEntity SET status =:status WHERE id = :id ")
    suspend fun updateSingleItem(status:Int, id:Int)

    @Query("UPDATE itemEntity SET reminderr =:status WHERE id = :id ")
    suspend fun updateSingleItemReminderDao(status:Int, id:Int)

    @Query("DELETE FROM itemEntity")
    suspend fun delete_all_item()

    @Delete
    suspend fun removeItem(itemsEntity: ItemsEntity)

    @Update
    suspend fun updateSingleItem( itemsEntity: ItemsEntity)

}