package com.pas.financetracker.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pas.financetracker.data.ProgressState
import com.pas.financetracker.data.repository.BaseRepository


abstract class BaseViewModel : ViewModel() {
    var actionLiveData: MutableLiveData<Int> = MutableLiveData()



    protected var TAG: String = javaClass.simpleName
    val sessionLiveData = MutableLiveData<Boolean>()
    val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()
    private val _messagesLiveData: MutableLiveData<String> = MutableLiveData()
    val messagesLiveData: LiveData<String> get() = _messagesLiveData





    abstract fun getRepository(): BaseRepository





    private fun logout() {

    }





    fun setSuccessMessage(message: String?) {
        message?.let {
            _messagesLiveData.value = it
        }
    }

    fun setErrorMessage(message: String?) {
        message?.let {
            _messagesLiveData.value = it
        }
    }







    fun setAction(action: Int) {
        actionLiveData.value = action
    }





}