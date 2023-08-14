package com.example.convidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.convidados.model.GuestModel
import com.example.convidados.repository.GuestRepository

class AllGuestsViewModel(application: Application) : AndroidViewModel(application) {
    //instancia uma vez para não precisar instanciar toda vez que eu chamar uma função
    private val repository = GuestRepository.getInstance(application.applicationContext)
    // Estou observando uma lista de guestModel
    private val listAllGuests = MutableLiveData<List<GuestModel>>()
    val guests: LiveData<List<GuestModel>> = listAllGuests

    fun getAll(){
    listAllGuests.value = repository.getAll()
    }
}