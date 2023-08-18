package com.example.xlmp12.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.xlmp12.BusScheduleApplication
import com.example.xlmp12.R
import com.example.xlmp12.databinding.FragmentFullScheduleBinding
import com.example.xlmp12.databinding.FragmentStopScheduleBinding
import com.example.xlmp12.viewmodels.BusScheduleViewModel
import com.example.xlmp12.viewmodels.BusScheduleViewModelFactory
import kotlinx.coroutines.launch

class StopScheduleFragment : Fragment() {
    private lateinit var binding: FragmentStopScheduleBinding

    private lateinit var stopName : String

    private val viewModel: BusScheduleViewModel by activityViewModels{
        BusScheduleViewModelFactory(
            (activity?.application as BusScheduleApplication).database.scheduleDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStopScheduleBinding.inflate(
            inflater,
            container,
            false
        )
        binding.lifecycleOwner = this@StopScheduleFragment
        arguments?.let {
            stopName = it.getString("stopName").toString()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = BusStopAdapter{}
        binding.stopRecyclerView.adapter = adapter
        lifecycle.coroutineScope.launch {
            viewModel.scheduleForStopName(stopName).collect{
                adapter.submitList(it)
            }
        }
    }
}