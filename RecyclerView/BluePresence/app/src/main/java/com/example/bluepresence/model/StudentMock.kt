package com.example.bluepresence.model

import com.example.bluepresence.data.Student

class StudentMock {
    var listStudents = ArrayList<Student>()

    init {
        for (i in 0..100) {
            listStudents.add(Student(i, i.toString()))
        }
    }

}