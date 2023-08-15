package com.example.convidados.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.convidados.R
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.databinding.ActivityGuestFormBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.viewmodel.GuestFormViewModel

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    // lateinit inicializa a variável futuramente, assim não tem necessidade de passar valor
    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var viewModel: GuestFormViewModel

    private var guestId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Instanciamos a viewModel, atrelando ela ao ciclo de vida. Assim, quando morre a activity, morre a viewmodel também
        viewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)

        binding.buttonSave.setOnClickListener(this)
        binding.radioPresence.isChecked = true

        //no caso de edição, carregamos os dados do item
        loadData()
        observe()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_save) {
            // Pego os valores via interface, guardo em variáveis temporárias e envio para viewModel
            val name = binding.editName.text.toString()
            val presence = binding.radioPresence.isChecked

            val model = GuestModel(guestId, name, presence)

            viewModel.save(model)
        }
    }

    private fun observe() {
        viewModel.guests.observe(this, Observer {
            binding.editName.setText(it.name)
            if (it.presence) {
                binding.radioPresence.isChecked = true

            } else {
                binding.radioAbsent.isChecked = true
            }
        })

        viewModel.saveGuest.observe(this, Observer {
            if (it != "") {
                Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            guestId = bundle.getInt(DataBaseConstants.GUEST.ID)
            viewModel.get(guestId)
        }
    }
}