package com.example.flowrspot.feature.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.flowrspot.R
import com.example.flowrspot.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var viewBinding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentHomeBinding.bind(view)
    }
}