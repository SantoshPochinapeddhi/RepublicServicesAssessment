package com.santosh.republicservices.ui.routes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.santosh.republicservices.databinding.FragmentRouteInfoBinding
import com.santosh.republicservices.ui.DriversAndRoutesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RouteFragment : Fragment() {
    private lateinit var binding: FragmentRouteInfoBinding

    private val viewModel: DriversAndRoutesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRouteInfoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getPossibleRouteForADriver(arguments?.getString("driverId", "").orEmpty()).collect { it ->
                    binding.routeName.text = it.name
                    binding.routeType.text = it.type
                }
            }
        }
    }
}