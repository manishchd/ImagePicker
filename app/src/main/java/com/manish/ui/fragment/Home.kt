package com.manish.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.manish.databinding.HomeBinding

class Home : Fragment() {

    private var _binding: HomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = HomeBinding.inflate(layoutInflater)
        return binding.root
    }
}