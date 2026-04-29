package com.uzuu.admin.feature.register

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
import com.uzuu.admin.databinding.FragmentRegisterBinding
import com.uzuu.admin.feature.MainActivity
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels {
        RegisterFactory((requireActivity() as MainActivity).container.authRepo)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupObservers()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnRegister.setOnClickListener {
            viewModel.onRegister(
                binding.edtFullName.text.toString().trim(),
                binding.edtUsername.text.toString().trim(),
                binding.edtEmail.text.toString().trim(),
                binding.edtPassword.text.toString().trim(),
                binding.edtConfirmPassword.text.toString().trim(),
                binding.edtPhone.text.toString().trim(),
                binding.edtAddress.text.toString().trim()
            )
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
                    binding.edtUsername.isEnabled = isEnabled
                    binding.edtPassword.isEnabled = isEnabled
                    binding.edtConfirmPassword.isEnabled = isEnabled
                    binding.edtEmail.isEnabled = isEnabled
                    binding.edtFullName.isEnabled = isEnabled
                    binding.btnRegister.isEnabled = isEnabled
                    binding.progress.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                    binding.btnRegister.text = if (state.isLoading) "" else "Đăng ký"
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect { event ->
                    when (event) {
                        is RegisterUiEvent.Toast ->
                            Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()

                        is RegisterUiEvent.NavigateToLogin ->
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
