package com.example.convidados.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.databinding.RowGuestBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.view.listener.OnGuestListener
import com.example.convidados.view.viewholder.GuestViewHolder

//GuestsAdapter extende de RecyclerView.Adapter e com isso sou obrigado a implementar os métodos da classe
//Além disso, precisamos passar um parâmetro chamado view holder, porém ele é abstrato, assim nós criamos uma VH para a gente
//A view holder é onde configuramos o layout de como cada item na lista será exibido
class GuestsAdapter : RecyclerView.Adapter<GuestViewHolder>() {
    private var guestList: List<GuestModel> = listOf()
    private lateinit var listener: OnGuestListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val item = RowGuestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GuestViewHolder(item, listener)
    }

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        holder.bind(guestList[position])
    }

    override fun getItemCount(): Int {
        return guestList.count()
    }

    //Recebe a lista de convidados
    fun updatedGuests(list: List<GuestModel>) {
        guestList = list
        //função que força atualizar a lista para toda atualização
        notifyDataSetChanged()
    }

    fun attachListener(guestListener: OnGuestListener) {
        listener = guestListener
    }
}