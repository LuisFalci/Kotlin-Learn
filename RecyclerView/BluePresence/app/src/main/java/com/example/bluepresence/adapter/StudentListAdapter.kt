package com.example.bluepresence.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bluepresence.R
import com.example.bluepresence.data.Student

//Defino um OnClickListener para cada um dos meus elementos criados
class StudentListAdapter(
    val listStudents: ArrayList<Student>,
    val onClickListener: OnClickListener
) :
    RecyclerView.Adapter<StudentListAdapter.StudentViewHolder>() {
    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.name_student)
    }

    class OnClickListener(val clickListener: (student: Student) -> Unit) {
        fun onClick(student: Student) = clickListener(student)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_student_list, parent, false)

        return StudentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listStudents.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        //Rodamos a lista setando para cada linha o nome do estudante
        val student = listStudents[position]
        holder.textView.setText(student.nome)

        //itemView é o elemento clicado. Definimos que ao clicar no elemento passamos o student que ele se refere
        holder.itemView.setOnClickListener {
            onClickListener.onClick(student)
        }
    }
}