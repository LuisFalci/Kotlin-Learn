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

    // Para deletar o convidado, eu preciso apenas do id então eu não passo o convidado inteiro como parâmetro
    fun delete(id: Int): Boolean {
        return try {
            val db = guestDataBase.writableDatabase

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            db.delete(DataBaseConstants.GUEST.TABLE_NAME, selection, args)
            true
        } catch (e: Exception) {
            false
        }

    }
    // ? permite que a função retorne nulo
    fun get(id: Int): GuestModel? {

        var guest: GuestModel? = null

        try {
            val db = guestDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor =
                db.query(
                    DataBaseConstants.GUEST.TABLE_NAME,
                    projection,
                    selection,
                    args,
                    null,
                    null,
                    null
                )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))

                    guest = GuestModel(id, name, presence == 1)
                }
            }
            cursor.close()
        } catch (e: Exception) {
            return guest
        }
        return guest

    }

    fun getAll(): List<GuestModel> {

        // Declaramos uma lista do tipo Guest a fim de retorna-la ao final com todos os convidados
        val list = mutableListOf<GuestModel>()

        try {
            val db = guestDataBase.readableDatabase

            // Lista dos campos que quero exibir
            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )
            // O cursor é como uma seta, que sempre aponta para o primeiro item da tabela no banco, e Lê linha a linha.
            // O cursor consegue retornar dados como número de linhas (itens)
            val cursor =
                db.query(
                    DataBaseConstants.GUEST.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
                )

            // Se o cursor existir (!= null) e possuir itens (count > 0)
            if (cursor != null && cursor.count > 0) {
                // Fica no loop enquanto o cursor estiver lendo/movendo linha a linha
                while (cursor.moveToNext()) {
                    // Pega a posição do vetor em que está o id
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))

                    //Após pegar os dados, montamos o objeto convidado e adicionamos ele na lista
                    list.add(GuestModel(id, name, presence == 1))
                }
            }
            // Após usar o cursor, precisamos fechar ele
            cursor.close()
        } catch (e: Exception) {
            return list
        }
        return list

    }

    fun getPresent(): List<GuestModel> {

        val list = mutableListOf<GuestModel>()

        try {
            val db = guestDataBase.readableDatabase

            // Lista dos campos que quero exibir
            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val selection = DataBaseConstants.GUEST.COLUMNS.PRESENCE + " = ?"
            // Quando present for igual a 1 eu vou retornar o convidado
            val args = arrayOf("1")

            // O selection e o args são os filtros que eu passo para o cursor selecionar para mim
            val cursor =
                db.query(
                    DataBaseConstants.GUEST.TABLE_NAME,
                    projection,
                    selection,
                    args,
                    null,
                    null,
                    null
                )

            // Segunda forma de fazer a querry no banco:
            // val cursor2 = db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 1", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))

                    list.add(GuestModel(id, name, presence == 1))
                }
            }
            cursor.close()
        } catch (e: Exception) {
            return list
        }
        return list

    }

    fun getAbsent(): List<GuestModel> {

        val list = mutableListOf<GuestModel>()

        try {
            val db = guestDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val selection = DataBaseConstants.GUEST.COLUMNS.PRESENCE + " = ?"
            val args = arrayOf("0")

            val cursor =
                db.query(
                    DataBaseConstants.GUEST.TABLE_NAME,
                    projection,
                    selection,
                    args,
                    null,
                    null,
                    null
                )

            // Segunda forma de fazer a querry no banco:
            // val cursor2 = db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 0", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))

                    list.add(GuestModel(id, name, presence == 1))
                }
            }
            cursor.close()
        } catch (e: Exception) {
            return list
        }
        return list

    }
}