package com.example.feature_supervisor_research.addnewtask

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.di.CoreInjectHelper
import com.example.core.model.Task
import com.example.feature_supervisor_research.addnewtask.recycler.TaskAdapter
import com.example.feature_supervisor_research.aspirantdetails.AspirantDetailsViewModel
import com.example.feature_supervisor_research.aspirantlist.ChangeGradeDialog
import com.example.feature_supervisor_research.databinding.FragmentAddNewTaskBinding
import com.example.feature_supervisor_research.di.DaggerFragmentsComponent
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddNewTaskFragment : Fragment() {

    private lateinit var binding: FragmentAddNewTaskBinding
    private lateinit var taskAdapter: TaskAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AddNewTaskViewModel> { viewModelFactory }

    private var listOfTasks = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDagger()
    }

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
        val researchId = arguments?.getString("123")
        val studentName = arguments?.getString("name")
        binding.noTasksImageView.isVisible = true
        binding.addTaskFloatingButton.setOnClickListener {
            AddTaskDialog() {task, selectedDate ->
                listOfTasks.add(Task(task, selectedDate,false))
                viewModel.updateTasksUseCase(listOfTasks, researchId!!)
                viewModel.getResearchByIdUseCase(researchId)
                taskAdapter.listOfTasks.submitList(listOfTasks)
            }.show(
                childFragmentManager,
                null
            )
        }
        viewModel.research.observe(viewLifecycleOwner) {
            if(it.listOfTasks.size > 0) {
                binding.noTasksImageView.isVisible = false
            }
            listOfTasks = it.listOfTasks
            binding.tasksRecyclerView.adapter?.notifyDataSetChanged()
            taskAdapter.listOfTasks.submitList(listOfTasks)
        }

        viewModel.getResearchByIdUseCase(researchId!!)
        binding.tasksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        taskAdapter = TaskAdapter() {
            listOfTasks.remove(it)
            taskAdapter.listOfTasks.submitList(listOfTasks)
            viewModel.updateTasksUseCase(listOfTasks, researchId)
            viewModel.getResearchByIdUseCase(researchId)
        }

        binding.tasksRecyclerView.adapter = taskAdapter
    }



    private fun initDagger() {
        DaggerFragmentsComponent.builder()
            .coreComponent(CoreInjectHelper.provideCoreComponent(applicationContext = requireActivity().applicationContext))
            .build().inject(this)
    }
}