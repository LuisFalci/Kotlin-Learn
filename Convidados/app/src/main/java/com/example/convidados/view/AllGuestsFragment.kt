package com.example.convidados.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.databinding.FragmentAllGuestsBinding
import com.example.convidados.view.adapter.GuestsAdapter
import com.example.convidados.view.listener.OnGuestListener
import com.example.convidados.viewmodel.GuestsViewModel

class AllGuestsFragment : Fragment() {

    private var _binding: FragmentAllGuestsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GuestsViewModel
    private var adapter = GuestsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        viewModel = ViewModelProvider(this).get(GuestsViewModel::class.java)
        _binding = FragmentAllGuestsBinding.inflate(inflater, container, false)

        //Layout da recyclerview
        binding.recyclerGuests.layoutManager = LinearLayoutManager(context)

        //Adapter da recyclerview
        binding.recyclerGuests.adapter = adapter

        //Classe anônima, implementamos a interface
        val lisener = object : OnGuestListener {
            override fun onClick(id: Int) {
                // GuestFormActivity é a tela de criação/edição
                val intent = Intent(context, GuestFormActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(DataBaseConstants.GUEST.ID, id)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                viewModel.delete(id)
            }

        }

        adapter.attachListener(lisener)

        viewModel.getAll()
        observe()
        return binding.root
    }

    //onResume garante que a lista seja atualizada quando a view voltar do segundo plano
    override fun onResume() {
        super.onResume()
        viewModel.getAll()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        //observe fica observando a variável "guests" que passamos para ele e quando retornar a listagem, executamos uma ação
        viewModel.guests.observe(viewLifecycleOwner) {
            // it é igual a lista, passamos para o adapter para exibir a lista
            adapter.updatedGuests(it)
        }
    }
}