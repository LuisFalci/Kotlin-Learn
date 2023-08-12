package com.example.convidados

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.convidados.databinding.ActivityGuestFormBinding

class GuestFormActivity : AppCompatActivity(),View.OnClickListener {

    // lateinit inicializa a variável futuramente, assim não tem necessidade de passar valor
    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var  viewModel: GuestFormViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Instanciamos a viewModel, atrelando ela ao ciclo de vida. Assim, quando morre a activity, morre a viewmodel também
        viewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)

        binding.buttonSave.setOnClickListener(this)
        binding.radioPresence.isChecked = true
    }

    override fun onClick(v: View) {
        if(v.id == R.id.button_save){

        }
    }
}