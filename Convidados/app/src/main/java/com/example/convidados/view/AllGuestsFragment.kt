package com.example.convidados.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.databinding.FragmentAllGuestsBinding
import com.example.convidados.view.adapter.GuestsAdapter
import com.example.convidados.viewmodel.AllGuestsViewModel

class AllGuestsFragment : Fragment() {

    private var _binding: FragmentAllGuestsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AllGuestsViewModel
    private var adapter = GuestsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        viewModel = ViewModelProvider(this).get(AllGuestsViewModel::class.java)
        _binding = FragmentAllGuestsBinding.inflate(inflater, container, false)

        //Layout da recyclerview
        binding.recyclerAllGuests.layoutManager = LinearLayoutManager(context)

        //Adapter da recyclerview
        binding.recyclerAllGuests.adapter = adapter

        viewModel.getAll()
        observe()
        return binding.root
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