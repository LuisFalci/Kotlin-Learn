package com.example.convidados.repository

import android.content.ContentValues
import android.content.Context
import com.example.convidados.model.GuestModel

// O Repository é onde manipulamos os dados, o database serve apenas para fazer a conexão com o banco

//deixamos a classe privada seguindo o padrão singleton
// O Singleton serve para controlar o número de instâncias de uma classe
class GuestRepository private constructor(context: Context) {

    // Ao instanciarmos GuestRepository, instanciamos GuestDataBase também.
    private val guestDataBase = GuestDataBase(context)

    // Singleton
    companion object {
        // não podemos instanciar desta forma pois precisamos do contexto que é passado como parâmetro
        // private val repository = GuestRepository()

        // lateinit permite inicializar a variável repository sem instanciar a classe. Só instanciamos depois de
        // verificar se a classe já foi instanciada
        private lateinit var repository: GuestRepository
        fun getInstance(context: Context): GuestRepository {
            // Instanciamos a classe caso não tenha sido instanciada
            if (!Companion::repository.isInitialized) {
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    // A função insert espera um convidado para poder inseri-lo no banco
    fun insert(guest: GuestModel) {
        val db = guestDataBase.writableDatabase

        val presence = if (guest.presence) 1 else 0

        val values = ContentValues()
        values.put("name", guest.name)
        values.put("presence", guest.presence)

        db.insert("Guest", null, values)
    }

    fun update() {
    }
}