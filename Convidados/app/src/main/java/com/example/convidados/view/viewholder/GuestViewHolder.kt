package com.example.convidados.view.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.databinding.RowGuestBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.view.listener.OnGuestListener

//Extendemos de view holder
// Criamos uma interface listener para aplicarmos as funções de cada item, como deletar e editar
class GuestViewHolder(private val bind: RowGuestBinding, private val listener: OnGuestListener) :
    RecyclerView.ViewHolder(bind.root) {
    //estamos acessando o xml pelo bind e fazendo o text ser igual ao nome do item
    fun bind(guest: GuestModel) {
        bind.textName.text = guest.name

        bind.textName.setOnClickListener {
            listener.onClick(guest.id)
        }

        bind.textName.setOnLongClickListener {
            listener.onDelete(guest.id)
            true
             }
    }
}