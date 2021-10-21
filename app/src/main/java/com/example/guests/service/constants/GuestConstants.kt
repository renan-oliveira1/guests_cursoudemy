package com.example.guests.service.constants

class GuestConstants private constructor(){
    companion object{
        val GUESTID = "guestID"
    }
    object FILTER{
        const val EMPTY = 0
        const val PRESENT = 1
        const val ABSENT = 2
    }
}