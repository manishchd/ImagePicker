package com.manish.ui.bottomsheets

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.manish.R
import com.manish.databinding.MediaPickerBinding
import com.manish.enums.ChooseFrom
import com.manish.enums.MediaType
import com.manish.interfaces.IMediaResult
import com.manish.utils.ImageUtils.checkPermissionGivenInManifest
import com.manish.utils.IntentUtils

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
        checkPermissionGivenInManifest(requireContext())
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
                if (Builder.isCameraPermissionRequired()) {
                    requestCameraPermission()
                } else {
                    IntentUtils.openCamera(result)
                }
            }
            R.id.tvGalleryPick -> {

            }
        }
    }

    private fun requestCameraPermission() {
        when {
            requireActivity().checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                IntentUtils.openCamera(result)
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(), android.Manifest.permission.CAMERA
            ) -> {
                view?.let {
                    Toast.makeText(context, "Camera permission is required to access this feature", Toast.LENGTH_SHORT).show()
                    Snackbar.make(
                        it,
                        "Camera permission is required to access this feature",
                        Snackbar.LENGTH_LONG
                    ).setAction(
                        "OK"
                    ) {
                        cameraPermissionResult.launch(android.Manifest.permission.CAMERA)
                    }.show()
                }
            }
            else -> {
                IntentUtils.openSetting(requireContext())
            }
        }
    }

    object Builder {

        private var contentType: String = MediaType.IMAGE.value
        private var chooseFrom: ChooseFrom = ChooseFrom.BOTH_CAMERA_AND_GALLERY
        private var mediaResult: IMediaResult? = null
        private var permissionArray: ArrayList<String> = ArrayList()
        private var cameraPermission: Boolean = false

        fun getContentType() = contentType
        fun isCameraPermissionRequired() = cameraPermission
        fun getChooseType() = chooseFrom
        fun getPermissions() = permissionArray

        fun checkCameraPermission(isRequired: Boolean): Builder {
            cameraPermission = isRequired
            return this
        }

        fun setMediaType(mediaType: MediaType): Builder {
            contentType = mediaType.value
            return this
        }

        fun setChooseFrom(chooseFrom: ChooseFrom): Builder {
            this.chooseFrom = chooseFrom
            return this
        }

        fun setPermissions(vararg permissions: String): Builder {
            permissionArray.clear()
            permissionArray.addAll(permissions.toList())
            return this
        }

        fun setMediaResultListener(mediaResult: IMediaResult): Builder {
            this.mediaResult = mediaResult
            return this
        }

        fun build() = MMediaPicker(mediaResult)

    }

    private val result =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.extras?.getParcelable("data", Bitmap::class.java)
            } else {
                it.data?.extras?.getParcelable("data")
            }
        }

    private val cameraPermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if(it==true){
                IntentUtils.openCamera(result)
            }
        }

}