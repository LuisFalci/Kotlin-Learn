package com.example.convidados.viewmodel

import androidx.lifecycle.ViewModel
import com.example.convidados.repository.GuestRepository

class GuestFormViewModel: ViewModel() {
    //Conexão com repository instanciado a classe GuestRepository (fica entre a ViewModel e a Model)
    private val repository = GuestRepository.getInstance()

    fun abc(){}
}