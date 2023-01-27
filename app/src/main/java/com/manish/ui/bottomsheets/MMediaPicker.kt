package com.manish.ui.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.manish.R
import com.manish.databinding.MediaPickerBinding
import com.manish.enums.ChooseFrom
import com.manish.enums.MediaType
import com.manish.interfaces.IMediaResult

class MMediaPicker(val mediaResult: IMediaResult?) : BottomSheetDialogFragment(),
    View.OnClickListener {

    private var _binding: MediaPickerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = MediaPickerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickHandler()
        setUi()
    }

    private fun setUi() {
        when (Builder.getChooseType()) {
            ChooseFrom.CAMERA -> {
                binding.tvGalleryPick.visibility = View.GONE
            }
            ChooseFrom.GALLERY -> {
                binding.tvCameraPick.visibility = View.VISIBLE
            }
            ChooseFrom.BOTH_CAMERA_AND_GALLERY -> {
                binding.tvCameraPick.visibility = View.VISIBLE
                binding.tvGalleryPick.visibility = View.GONE
            }
        }
    }

    private fun clickHandler() {
        binding.tvCameraPick.setOnClickListener(this)
        binding.tvGalleryPick.setOnClickListener(this)
    }

    override fun getTheme(): Int = R.style.TransparentSheet

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvCameraPick -> {

            }
            R.id.tvGalleryPick -> {

            }
        }
    }

    object Builder {

        private var contentType: String = MediaType.IMAGE.value
        private var chooseFrom: ChooseFrom = ChooseFrom.BOTH_CAMERA_AND_GALLERY
        private var mediaResult: IMediaResult? = null

        fun setMediaType(mediaType: MediaType): Builder {
            contentType = mediaType.value
            return this
        }

        fun getContentType() = contentType
        fun getChooseType() = chooseFrom

        fun setChooseFrom(chooseFrom: ChooseFrom): Builder {
            this.chooseFrom = chooseFrom
            return this
        }

        fun setMediaResultListener(mediaResult: IMediaResult): Builder {
            this.mediaResult = mediaResult
            return this
        }

        fun build() = MMediaPicker(mediaResult)

    }


}