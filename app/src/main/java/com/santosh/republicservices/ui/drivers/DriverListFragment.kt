package com.santosh.republicservices.ui.drivers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.santosh.republicservices.model.data.NetworkResult
import com.santosh.republicservices.databinding.FragmentDriverListBinding
import com.santosh.republicservices.ui.DriversAndRoutesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DriverListFragment : Fragment() {
    private lateinit var binding: FragmentDriverListBinding
    private lateinit var adapter: DriversAdapter
    private val viewModel: DriversAndRoutesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDriverListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sort.setOnClickListener {
            adapter.setDrivers(viewModel.getSortedList())
        }
        adapter = DriversAdapter() {
            val action = DriverListFragmentDirections.actionDriverListToRouteInfo(it.id)
            findNavController().navigate(action)
        }
        binding.driverList.adapter = adapter
        fetchDriversList()
    }

    private fun fetchDriversList() {
        binding.progressbar.isVisible = true
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fetchDrivers().collect { it ->
                    when (it) {
                        is NetworkResult.Loading -> {
                            binding.progressbar.isVisible = it.isLoading
                        }
                        is NetworkResult.Failure -> {
                            Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT)
                                .show()
                            binding.progressbar.isVisible = false
                        }
                        is NetworkResult.Success -> {
                          adapter.setDrivers(it.data)
                          binding.progressbar.isVisible = false
                        }
                    }
                }
            }
        }
    }
}