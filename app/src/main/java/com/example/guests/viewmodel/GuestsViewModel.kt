package com.example.guests.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.guests.service.constants.GuestConstants
import com.example.guests.service.model.GuestModel
import com.example.guests.service.repository.GuestRepository

class GuestsViewModel(application: Application) : AndroidViewModel(application) {

    private val mGuestRepository = GuestRepository(application.applicationContext)

    private val mGuestList = MutableLiveData<List<GuestModel>>()
    val guestList: LiveData<List<GuestModel>> = mGuestList

    fun load(filter: Int){
        if(filter == GuestConstants.FILTER.EMPTY) {
            mGuestList.value = mGuestRepository.getAll()
        }else if(filter == GuestConstants.FILTER.PRESENT){
            mGuestList.value = mGuestRepository.getPresents()
        }else{
            mGuestList.value = mGuestRepository.getAbsent()
        }
    }

    fun delete(id: Int){
        val guest: GuestModel= mGuestRepository.getGuestById(id)
        mGuestRepository.delete(guest)
    }
}