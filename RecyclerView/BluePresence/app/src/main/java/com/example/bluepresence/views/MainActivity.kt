package com.example.bluepresence.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bluepresence.adapter.StudentListAdapter
import com.example.bluepresence.databinding.ActivityMainBinding
import com.example.bluepresence.model.StudentMock

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewStudent.layoutManager = LinearLayoutManager(this)

        val mock = StudentMock()

        // it (it.name) é o mesmo que definir uma variável student -> student.name
//        binding.recyclerViewStudent.adapter = StudentListAdapter(mock.listStudents, StudentListAdapter.OnClickListener{
//            Toast.makeText(this, it.nome, Toast.LENGTH_SHORT).show()
//        })
        binding.recyclerViewStudent.adapter = StudentListAdapter(mock.listStudents, StudentListAdapter.OnClickListener{student ->
            Toast.makeText(this, student.nome, Toast.LENGTH_SHORT).show()
        })
    }
}