package com.example.nit3213app.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.nit3213app.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DashboardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        val errorText = view.findViewById<TextView>(R.id.errorText)

        adapter = DashboardAdapter { entity ->
            val bundle = Bundle()
            bundle.putParcelable("entity", entity)
            findNavController().navigate(
                R.id.action_dashboardFragment_to_detailsFragment,
                bundle
            )
        }
        recyclerView.adapter = adapter

        val keypass = arguments?.getString("keypass") ?: ""
        viewModel.getDashboard(keypass)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.entitiesState.collect { entities ->
                        adapter.setData(entities)
                    }
                }
                launch {
                    viewModel.errorState.collect { error ->
                        if (error != null) {
                            errorText.text = error
                            errorText.visibility = View.VISIBLE
                        } else {
                            errorText.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
}