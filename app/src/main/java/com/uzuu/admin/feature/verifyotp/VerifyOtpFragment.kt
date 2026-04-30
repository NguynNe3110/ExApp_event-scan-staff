package com.uzuu.admin.feature.verifyotp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.uzuu.admin.R
import com.uzuu.admin.databinding.FragmentVerifyOtpBinding
import com.uzuu.admin.feature.MainActivity
import kotlinx.coroutines.launch

class VerifyOtpFragment : Fragment() {

    private var _binding: FragmentVerifyOtpBinding? = null
    val binding get() = _binding!!

    private val viewModel: VerifyOtpViewModel by viewModels {
        VerifyOtpFactory((requireActivity() as MainActivity).container.authRepo)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerifyOtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val email = arguments?.getString("email") ?: ""
        viewModel.setEmail(email)
        binding.txtEmailHint.text = email
        setupObservers()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnVerify.setOnClickListener {
            viewModel.onVerify(binding.edtOtp.text.toString().trim())
        }

        binding.txtBack.setOnClickListener {
            viewModel.onBack()
        }

        binding.txtResend.setOnClickListener {
            viewModel.onResendOtp()
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    val isEnabled = !state.isLoading
                    binding.edtOtp.isEnabled = isEnabled
                    binding.btnVerify.isEnabled = isEnabled
                    binding.txtResend.isEnabled = isEnabled
                    binding.progress.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                    binding.btnVerify.text = if (state.isLoading) "" else "Xác thực"
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect { event ->
                    when (event) {
                        is VerifyOtpUiEvent.Toast ->
                            Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()

                        is VerifyOtpUiEvent.NavigateToLogin ->
                            findNavController().navigate(
                                R.id.action_verifyOtp_to_login
                            )

                        is VerifyOtpUiEvent.NavigateBack ->
                            findNavController().popBackStack()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
