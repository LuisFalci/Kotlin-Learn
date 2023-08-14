package com.example.convidados.repository

import android.content.ContentValues
import android.content.Context
import com.example.convidados.constants.DataBaseConstants
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
    // Aqui na repository é onde de fato realizamos as operações com os dados, neste caso, inserir no banco o usuário
    fun insert(guest: GuestModel): Boolean {
        //mudamos a função para Boolean, então ela espera um retorno booleano
        return try {
            val db = guestDataBase.writableDatabase

            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, guest.presence)

            db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, values)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun update(guest: GuestModel): Boolean {
        return try {
            val db = guestDataBase.writableDatabase

            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, guest.presence)

            // O selection interpola com o args. Neste caso, o update será feito quando o id for igual ao do args.
            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            // O comando sql precisa ser feito com string, por isso convertemos o id para string
            val args = arrayOf(guest.id.toString())

            db.update(DataBaseConstants.GUEST.TABLE_NAME, values, selection, args)
            true
        } catch (e: Exception) {
            false
        }

    }
}