package com.buddy.revision.Respositories

import androidx.lifecycle.LiveData
import com.buddy.revision.Daos.ItemDao
import com.buddy.revision.Daos.RegistrationDao
import com.buddy.revision.Entities.ItemsEntity
import com.buddy.revision.Entities.RegistrationEntities


class ItemsRepository(
    private val itemDao: ItemDao,
    private val registrationDao: RegistrationDao,
) {


    fun getItemDao(type:String): LiveData<List<ItemsEntity>> = itemDao.getItems(type)

    val getRegistrationDao: LiveData<List<RegistrationEntities>> = registrationDao.getUsers()

    suspend fun insertItemsDao(itemsEntity: ItemsEntity) {
        itemDao.insertItem(itemsEntity)
    }

    suspend fun updateItemDao(status: Int, id: Int) {
        itemDao.updateSingleItem(status, id)
    }

    suspend fun updateSingleItemReminder(status: Int, id: Int) {
        itemDao.updateSingleItemReminderDao(status, id)
    }

    suspend fun updatethemeDao(theme: String, id: Int) {
        registrationDao.updatetheme(theme, id)
    }

    suspend fun delete_All_item_from_dao() {
        itemDao.delete_all_item()
    }

    suspend fun removeItemDao(itemsEntity: ItemsEntity) {
        itemDao.removeItem(itemsEntity)
    }

    suspend fun updateSingleItemDao(itemsEntity: ItemsEntity) {
        itemDao.updateSingleItem(itemsEntity)
    }

    suspend fun insertRegistrationUsersDao(registrationEntities: RegistrationEntities) {
        registrationDao.insertUser(registrationEntities)
    }

    suspend fun upateRegistrationUsersDao(registrationEntities: RegistrationEntities) {
        registrationDao.updateUser(registrationEntities)
    }

    suspend fun updateIsloggedIn(isLoggedin: Int, id: Int) = registrationDao.updateuIsLoggedIn(isLoggedin, id)

    fun wordQuery(word: String): LiveData<List<ItemsEntity>> = itemDao.search_item(word)

}