package com.example.feature_supervisor_research.addnewtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.model.Task
import com.example.feature_supervisor_research.addnewtask.recycler.TaskAdapter
import com.example.feature_supervisor_research.databinding.FragmentAddNewTaskBinding

class AddNewTaskFragment : Fragment() {

    private lateinit var binding: FragmentAddNewTaskBinding
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewTaskBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addTaskFloatingButton.setOnClickListener {
            Toast.makeText(requireContext(), "HEY", Toast.LENGTH_SHORT).show()
        }
        binding.tasksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        taskAdapter = TaskAdapter()
        taskAdapter.listOfTasks = listOf(Task("123"))
        binding.tasksRecyclerView.adapter = taskAdapter
    }
}