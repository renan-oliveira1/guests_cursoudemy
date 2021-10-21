package com.example.guests.service.repository


import android.content.Context
import com.example.guests.service.model.GuestModel

class GuestRepository (context: Context){

    private val mDataBase = GuestDataBase.getDataBase(context).guestDAO()


    fun getAll(): List<GuestModel>{
        return mDataBase.getAll()
    }

    fun getPresents(): List<GuestModel>{
        return mDataBase.getPresents()
    }

    fun getAbsent(): List<GuestModel>{
        return mDataBase.getAbsents()
    }

    fun getGuestById(id: Int): GuestModel{
        return mDataBase.getGuestById(id)
    }

    fun save(guest: GuestModel): Boolean {
        return mDataBase.save(guest) > 0
    }

    fun update(guest: GuestModel): Boolean{
        return mDataBase.update(guest) > 0
    }

    fun delete(guest: GuestModel){
        mDataBase.delete(guest)
    }
}