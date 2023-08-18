package com.example.xlmp12.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.xlmp12.BusScheduleApplication
import com.example.xlmp12.databinding.FragmentFullScheduleBinding
import com.example.xlmp12.viewmodels.BusScheduleViewModel
import com.example.xlmp12.viewmodels.BusScheduleViewModelFactory
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FullScheduleFragment : Fragment() {
    private lateinit var binding: FragmentFullScheduleBinding

    private val viewModel: BusScheduleViewModel by activityViewModels {
        BusScheduleViewModelFactory(
            (activity?.application as BusScheduleApplication).database.scheduleDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFullScheduleBinding.inflate(
            inflater,
            container,
            false
        )
        binding.lifecycleOwner = this@FullScheduleFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val busStopAdapter = BusStopAdapter{
            val action = FullScheduleFragmentDirections
                .actionFullScheduleFragmentToStopScheduleFragment(
                    stopName = it.stopName
                )
            view.findNavController().navigate(action)
        }

        binding.fullRecyclerView.adapter = busStopAdapter

        lifecycle.coroutineScope.launch{
            viewModel.fullSchedule().collect(){
                busStopAdapter.submitList(it)
            }
        }
    }

}