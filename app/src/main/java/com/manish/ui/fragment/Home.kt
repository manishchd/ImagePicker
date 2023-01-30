package com.manish.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.manish.databinding.HomeBinding
import com.manish.enums.ChooseFrom
import com.manish.enums.MediaType
import com.manish.interfaces.IMediaResult
import com.manish.ui.bottomsheets.MMediaPicker
import java.io.File

class Home : Fragment() {

    private var _binding: HomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = HomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickHandler()
    }

    private fun clickHandler() {
        binding.btSubmit.setOnClickListener {

            val mediaPicker =
                MMediaPicker.Builder.setMediaType(MediaType.IMAGE).setChooseFrom(ChooseFrom.CAMERA)
                    .checkCameraPermission(true).setMediaResultListener(object : IMediaResult {
                        override fun onSuccess(file: File) {

                        }
                    }).build()
            mediaPicker.show(childFragmentManager, "MediaPicker")
        }
    }


}