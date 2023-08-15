package com.example.convidados.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.view.viewholder.GuestViewHolder

//GuestsAdapter extende de RecyclerView.Adapter e com isso sou obrigado a implementar os métodos da classe
//Além disso, precisamos passar um parâmetro chamado view holder, porém ele é abstrato, assim nós criamos uma VH para a gente
//A view holder é onde configuramos o layout de como cada item na lista será exibido
class GuestsAdapter: RecyclerView.Adapter<GuestViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}