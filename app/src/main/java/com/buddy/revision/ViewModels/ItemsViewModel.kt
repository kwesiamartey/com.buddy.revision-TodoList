package com.buddy.revision.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.buddy.revision.DatabaseHelper.ItemsDatabase
import com.buddy.revision.Entities.ItemsEntity
import com.buddy.revision.Entities.RegistrationEntities
import com.buddy.revision.Respositories.ItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemsViewModel(application: Application) : AndroidViewModel(application) {

    val getRegistrationDaoo:LiveData<List<RegistrationEntities>>?
    val repository : ItemsRepository
    val user_id:Int
    var item_id:Int? = null
    var txt_update_title:String? =null
    var txt_update_description: String? =null
    var txt_update_calender: String? =null
    var status:Int? = null
    var key_word:String? = null
    var type:String?=null
    init {
        val getDBDaa = ItemsDatabase.invoke(application).getDao()
        val getRegistrationDao = ItemsDatabase.invoke(application).getRegistrationDao()

        user_id = 0
        repository = ItemsRepository(getDBDaa, getRegistrationDao)
        getRegistrationDaoo = repository.getRegistrationDao

    }
    fun getRepoAllItemDao(type:String) : LiveData<List<ItemsEntity>> = repository.getItemDao(type)
    fun getQueryKeyWord(keyWord:String):LiveData<List<ItemsEntity>> = repository.wordQuery(keyWord)
    fun updateYourSingleItem( itemsEntity: ItemsEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSingleItemDao(itemsEntity)
        }
    }
    fun insertYourItems(itemsEntity: ItemsEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertItemsDao(itemsEntity)
        }
    }
    fun updateYourItems(num:Int, id:Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateItemDao(num, id)
        }
    }
    fun updateSingleItemReminderRepo(num:Int, id:Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateSingleItemReminder(num, id)
        }
    }
    fun deleteAllItems(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete_All_item_from_dao()
        }
    }
    fun removeYourItem(itemsEntity: ItemsEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.removeItemDao(itemsEntity)
        }
    }
    fun insetRegistrationsUser(registrationEntities: RegistrationEntities){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertRegistrationUsersDao(registrationEntities)
        }
    }
    fun updateRegistrationUser(registrationEntities: RegistrationEntities){
        viewModelScope.launch(Dispatchers.IO) {
            repository.upateRegistrationUsersDao(registrationEntities)
        }
    }
    fun updateIsLogged(isloggedin:Int, id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateIsloggedIn(isloggedin, id)
        }
    }
    fun updatethemeDaoRepo(theme:String, id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatethemeDao(theme, id)
        }
    }

}