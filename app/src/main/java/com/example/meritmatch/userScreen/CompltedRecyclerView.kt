package com.example.meritmatch.userScreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meritmatch.R
import com.example.meritmatch.retrofit.dataclass.Task
import com.example.meritmatch.userScreen.recyclerVIew.TaskAdapter

//class CompltedRecyclerView : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        val tasks = listOf(
//            Task(1, "Task 1", "Description for Task 1", 10.0, false, false),
//            Task(2, "Task 2", "Description for Task 2", 20.0, false, false),
//            Task(3, "Task 3", "Description for Task 3", 30.0, false, false)
//        )
//
//        val adapter = TaskAdapter(tasks)
//        recyclerView.adapter = adapter
//    }
//}

