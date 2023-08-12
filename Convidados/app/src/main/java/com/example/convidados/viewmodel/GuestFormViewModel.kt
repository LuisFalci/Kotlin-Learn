package com.example.convidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.convidados.repository.GuestRepository

//A viewModel não tem contexto, quem tem contexto é o AndroidViewModel com o parametro application fazendo o papel do context
// Precisamos do contexto para instanciar o repository e o database

class GuestFormViewModel(application: Application) : AndroidViewModel(application) {
    //Conexão com repository instanciado a classe GuestRepository (fica entre a ViewModel e a Model)
    private val repository = GuestRepository.getInstance(application)

    fun abc(){}
}