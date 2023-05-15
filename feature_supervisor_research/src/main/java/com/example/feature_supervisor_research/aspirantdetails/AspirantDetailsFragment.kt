package com.example.feature_supervisor_research.aspirantdetails

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.di.CoreInjectHelper
import com.example.core.model.Article
import com.example.feature_supervisor_research.aspirantdetails.recycler.AspirantDetailsAdapter
import com.example.feature_supervisor_research.databinding.FragmentAspirantResearchDetailsBinding
import com.example.feature_supervisor_research.di.DaggerFragmentsComponent
import javax.inject.Inject

class AspirantDetailsFragment : Fragment() {

    private lateinit var binding: FragmentAspirantResearchDetailsBinding
    private lateinit var aspirantDetailsAdapter: AspirantDetailsAdapter
    private var listOfArticles = mutableListOf<Article>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AspirantDetailsViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDagger()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAspirantResearchDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = arguments?.getString("123")

        viewModel.getInformation(name!!)
        viewModel.listOfResearch.observe(viewLifecycleOwner) {
            listOfArticles = it as MutableList<Article>
            aspirantDetailsAdapter.listOfArticles.submitList(it)
        }
        binding.researchDetailsRecyclerView.layoutManager = LinearLayoutManager(context)
        aspirantDetailsAdapter = AspirantDetailsAdapter({ article, position ->
            article.status = "Approve"
            listOfArticles[position] = article
            viewModel.updateArticle(name, listOfArticles)
            aspirantDetailsAdapter.notifyItemChanged(position)
        }, { article, position ->
            article.status = "Decline"
            listOfArticles[position] = article
            viewModel.updateArticle(name, listOfArticles)
            aspirantDetailsAdapter.notifyItemChanged(position)
        }, {
            openLink(it, requireContext())
        })
        binding.researchDetailsRecyclerView.adapter = aspirantDetailsAdapter
    }

    private fun openLink(url: String, context: Context) {
        try {
            val intent = { Intent(Intent.ACTION_VIEW, Uri.parse(url)) }
            context.startActivity(intent.invoke())
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "Неможливо відкрити посилання", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initDagger() {
        DaggerFragmentsComponent.builder()
            .coreComponent(CoreInjectHelper.provideCoreComponent(applicationContext = requireActivity().applicationContext))
            .build().inject(this)
    }
}