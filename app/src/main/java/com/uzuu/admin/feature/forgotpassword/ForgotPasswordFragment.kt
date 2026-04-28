package com.uzuu.admin.feature.forgotpassword

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
import com.uzuu.admin.databinding.FragmentForgotPasswordBinding
import com.uzuu.admin.feature.MainActivity
import kotlinx.coroutines.launch

class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    val binding get() = _binding!!

    private val viewModel: ForgotPasswordViewModel by viewModels {
        ForgotPasswordFactory((requireActivity() as MainActivity).container.authRepo)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupObservers()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnSubmit.setOnClickListener {
            viewModel.onSubmit(binding.edtEmail.text.toString().trim())
        }

        binding.txtLogin.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    val isEnabled = !state.isLoading
                    binding.edtEmail.isEnabled = isEnabled
                    binding.btnSubmit.isEnabled = isEnabled
                    binding.progress.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                    binding.btnSubmit.text = if (state.isLoading) "" else "Gửi yêu cầu"
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect { event ->
                    when (event) {
                        is ForgotPasswordUiEvent.Toast ->
                            Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()

                        is ForgotPasswordUiEvent.NavigateToLogin ->
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
