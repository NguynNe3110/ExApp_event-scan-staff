package com.uzuu.admin.feature.scan

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.uzuu.admin.R
import com.uzuu.admin.databinding.FragmentScanBinding
import com.uzuu.admin.feature.MainActivity
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@OptIn(androidx.camera.core.ExperimentalGetImage::class)
class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    val binding get() = _binding!!

    private val viewModel: ScanViewModel by viewModels {
        val container = (requireActivity() as MainActivity).container
        ScanFactory(container.checkInRepo, container.profileRepo)
    }

    private lateinit var cameraExecutor: ExecutorService
    private var cameraProvider: ProcessCameraProvider? = null
    private var cameraPreview: Preview? = null
    private var cameraAnalysis: ImageAnalysis? = null

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) startCamera()
        else Toast.makeText(context, "Cần cấp quyền Camera để quét QR", Toast.LENGTH_LONG).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cameraExecutor = Executors.newSingleThreadExecutor()

        viewModel.init()
        setupButtons()
        observeState()
        observeEvent()
        checkCameraPermission()
    }

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> startCamera()

            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    @OptIn(ExperimentalGetImage::class)
    private fun startCamera() {
        val currentBinding = _binding ?: return
        val currentContext = context ?: return
        val lifecycleOwner = viewLifecycleOwner
        val executor = cameraExecutor

        try {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(currentContext)
            cameraProviderFuture.addListener({
                // Ignore callbacks belonging to a destroyed or replaced View.
                if (_binding !== currentBinding ||
                    lifecycleOwner.lifecycle.currentState == Lifecycle.State.DESTROYED ||
                    executor.isShutdown
                ) return@addListener

                try {
                    val provider = cameraProviderFuture.get()
                    val selector = CameraSelector.DEFAULT_BACK_CAMERA
                    if (!provider.hasCamera(selector)) {
                        Toast.makeText(
                            currentContext,
                            "Không tìm thấy camera sau. Bạn có thể nhập mã vé thủ công.",
                            Toast.LENGTH_LONG
                        ).show()
                        return@addListener
                    }

                    cameraAnalysis?.clearAnalyzer()
                    cameraPreview?.let { cameraProvider?.unbind(it) }
                    cameraAnalysis?.let { cameraProvider?.unbind(it) }
                    cameraProvider = provider

                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(currentBinding.previewView.surfaceProvider)
                    }
                    val imageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also { it.setAnalyzer(executor, ::processImageProxy) }
                    cameraPreview = preview
                    cameraAnalysis = imageAnalysis

                    provider.bindToLifecycle(lifecycleOwner, selector, preview, imageAnalysis)
                } catch (e: Exception) {
                    android.util.Log.e("ScanFragment", "Camera initialization failed", e)
                    Toast.makeText(
                        currentContext,
                        "Không thể mở camera. Bạn có thể nhập mã vé thủ công.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }, ContextCompat.getMainExecutor(currentContext))
        } catch (e: Exception) {
            android.util.Log.e("ScanFragment", "Camera initialization failed", e)
            Toast.makeText(
                currentContext,
                "Không thể mở camera. Bạn có thể nhập mã vé thủ công.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    @OptIn(ExperimentalGetImage::class)
    @Suppress("UnsafeOptInUsageError")
    private fun processImageProxy(imageProxy: androidx.camera.core.ImageProxy) {
        val scanner = BarcodeScanning.getClient()
        @Suppress("UnsafeOptInUsageError")
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val inputImage = InputImage.fromMediaImage(
                mediaImage, imageProxy.imageInfo.rotationDegrees
            )
            scanner.process(inputImage)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        if (barcode.format == Barcode.FORMAT_QR_CODE) {
                            val rawValue = barcode.rawValue
                            if (!rawValue.isNullOrBlank()) {
                                viewModel.onQrScanned(rawValue)
                            }
                        }
                    }
                }
                .addOnCompleteListener { imageProxy.close() }
        } else {
            imageProxy.close()
        }
    }

    private fun setupButtons() {
        // Nhập tay mã vé
        binding.btnManualCheckIn.setOnClickListener {
            val code = binding.edtManualCode.text.toString()
            viewModel.onManualCheckIn(code)
            binding.edtManualCode.text?.clear()
        }

        // Quét tiếp
        binding.btnRescan.setOnClickListener {
            viewModel.onResetScan()
            binding.edtManualCode.text?.clear()
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->

                    // Tên người dùng
                    binding.txtUsername.text = "Xin chào: ${state.username}"

                    // Loading spinner
                    binding.progress.visibility =
                        if (state.isLoading) View.VISIBLE else View.GONE

                    // Scanner overlay hint
                    binding.txtScanHint.text = when {
                        state.isLoading      -> "Đang kiểm tra vé..."
                        !state.isScanEnabled -> "Xong — chờ quét tiếp..."
                        else                 -> "Hướng camera vào mã QR trên vé"
                    }

                    // Card kết quả
                    when (state.resultStatus) {
                        ResultStatus.IDLE -> {
                            binding.cardResult.visibility = View.GONE
                            binding.btnRescan.visibility  = View.GONE
                        }
                        ResultStatus.SUCCESS -> {
                            binding.cardResult.visibility = View.VISIBLE
                            binding.btnRescan.visibility  = View.VISIBLE

                            val r = state.lastResult!!
                            binding.cardResult.setCardBackgroundColor(
                                ContextCompat.getColor(requireContext(), R.color.result_success_bg)
                            )
                            binding.txtResultIcon.text       = "✅"
                            binding.txtResultStatus.text     = "CHECK-IN THÀNH CÔNG"
                            binding.txtResultStatus.setTextColor(
                                ContextCompat.getColor(requireContext(), R.color.result_success_text)
                            )
                            binding.txtResultEventName.text  = r.eventName
                            binding.txtResultTicketType.text = r.ticketTypeName
                            binding.txtResultCode.text       = "Mã: ${r.ticketCode}"
                            binding.txtResultUsedAt.text =
                                if (r.usedAt != null) "Dùng lúc: ${r.usedAt}" else ""
                            binding.txtResultUsedAt.visibility =
                                if (r.usedAt != null) View.VISIBLE else View.GONE
                        }
                        ResultStatus.FAIL -> {
                            binding.cardResult.visibility = View.VISIBLE
                            binding.btnRescan.visibility  = View.VISIBLE

                            binding.cardResult.setCardBackgroundColor(
                                ContextCompat.getColor(requireContext(), R.color.result_fail_bg)
                            )
                            binding.txtResultIcon.text       = "❌"
                            binding.txtResultStatus.text     = "CHECK-IN THẤT BẠI"
                            binding.txtResultStatus.setTextColor(
                                ContextCompat.getColor(requireContext(), R.color.result_fail_text)
                            )
                            binding.txtResultEventName.text  = ""
                            binding.txtResultTicketType.text = ""
                            binding.txtResultCode.text       = ""
                            binding.txtResultUsedAt.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun observeEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect { event ->
                    when (event) {
                        is ScanUiEvent.Toast ->
                            Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        cameraAnalysis?.clearAnalyzer()
        cameraPreview?.let { cameraProvider?.unbind(it) }
        cameraAnalysis?.let { cameraProvider?.unbind(it) }
        cameraPreview = null
        cameraAnalysis = null
        cameraProvider = null
        if (::cameraExecutor.isInitialized) cameraExecutor.shutdown()
        super.onDestroyView()
    }
}