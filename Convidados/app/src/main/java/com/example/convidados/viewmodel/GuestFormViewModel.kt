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

    // Atribuindo valores para saveGuest, com o LiveData, esta variável passa a ser observada
    private val _saveGuest = MutableLiveData<String>()
    val saveGuest: LiveData<String> = _saveGuest

    // Recebe um convidado de parâmetro
    fun save(guest: GuestModel) {
        // recebo os dados da view aqui na viewModel e envio para o repository
        // caso o id seja zero, é porque queremos criar um convidado. Se o id não for zero será um update
        if (guest.id == 0) {
            if (repository.insert(guest)) {
                _saveGuest.value = "Inserção com sucesso"
            } else {
                _saveGuest.value = "Falha"
            }

        } else {
            if (repository.update(guest)) {
                _saveGuest.value = "Atualização com sucesso"
            } else {
                _saveGuest.value = "Falha"
            }
        }
    }

    fun get(id: Int) {
        guestModel.value = repository.get(id)
    }
}