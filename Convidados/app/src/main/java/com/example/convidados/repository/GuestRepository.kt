package com.example.convidados.repository

//deixamos a classe privada seguindo o padrão singleton
// O Singleton serve para controlar o número de instâncias de uma classe
class GuestRepository private constructor() {


    companion object {
        // não podemos instanciar desta forma pois precisamos do contexto que é passado como parâmetro
        // private val repository = GuestRepository()

        // lateinit permite inicializar a variável repository sem instanciar a classe. Só instanciamos depois de
        // verificar se a classe já foi instanciada
         private lateinit var repository: GuestRepository
        fun getInstance(): GuestRepository {
            // Instanciamos a classe caso não tenha sido instanciada
            if(!Companion::repository.isInitialized){
                repository = GuestRepository()
            }
            return repository
        }
    }
    fun save(){
        GuestDataBase(null)
    }
}