package com.example.convidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.convidados.model.GuestModel
import com.example.convidados.repository.GuestRepository

//A viewModel não tem contexto, quem tem contexto é o AndroidViewModel com o parametro application fazendo o papel do context
// Precisamos do contexto para instanciar o repository e o database

class GuestFormViewModel(application: Application) : AndroidViewModel(application) {
    //Conexão com repository instanciado a classe GuestRepository (fica entre a ViewModel e a Model)
    private val repository = GuestRepository.getInstance(application)

    private val guestModel = MutableLiveData<GuestModel>()
    val guests: LiveData<GuestModel> = guestModel

    // Recebe um convidado de parâmetro
    fun insert(guest: GuestModel){
        // recebo os dados da view aqui na viewModel e envio para o repository
        repository.insert(guest)
    }

    fun get(id: Int) {
        repository.get(id)
    }
}