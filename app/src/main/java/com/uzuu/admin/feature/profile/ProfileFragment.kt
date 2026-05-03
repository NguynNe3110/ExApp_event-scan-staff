package com.uzuu.admin.feature.profile

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
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.uzuu.admin.R
import com.uzuu.admin.databinding.FragmentProfileBinding
import com.uzuu.admin.feature.MainActivity
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels {
        ProfileFactory((requireActivity() as MainActivity).container.profileRepo)
    }

    private lateinit var scanHistoryAdapter: ScanHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
        setupRecyclerView()
        setupButtons()
        observeState()
        observeEvent()
    }

    private fun setupRecyclerView() {
        scanHistoryAdapter = ScanHistoryAdapter()
        binding.rvScanHistory.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = scanHistoryAdapter
        }
    }

    private fun setupButtons() {
        binding.btnEditProfile.setOnClickListener {
            viewModel.onEditProfileClick()
        }
        binding.btnChangePassword.setOnClickListener {
            viewModel.onChangePasswordClick()
        }
        binding.btnLogout.setOnClickListener {
            viewModel.onLogoutClick()
        }
        binding.btnClearScanHistory.setOnClickListener {
            showClearHistoryConfirmation()
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.txtUsername.text = state.username
                    binding.txtAccountUsername.text = state.username
                    
                    state.profile?.let { profile ->
                        binding.txtFullName.text = profile.fullName.ifEmpty { "Chưa cập nhật" }
                        binding.txtEmail.text = profile.email.ifEmpty { "Chưa cập nhật" }
                        binding.txtPhone.text = profile.phone.ifEmpty { "Chưa cập nhật" }
                        binding.txtAddress.text = profile.address.ifEmpty { "Chưa cập nhật" }
                    }

                    scanHistoryAdapter.submitList(state.scanHistory)
                    
                    binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                }
            }
        }
    }

    private fun observeEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect { event ->
                    when (event) {
                        is ProfileUiEvent.NavigateToLogin -> {
                            navigateToLogin()
                        }
                        is ProfileUiEvent.ShowMessage -> {
                            Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                        }
                        is ProfileUiEvent.ShowError -> {
                            Toast.makeText(requireContext(), event.message, Toast.LENGTH_LONG).show()
                        }
                        is ProfileUiEvent.ShowEditProfileDialog -> {
                            showEditProfileDialog()
                        }
                        is ProfileUiEvent.ShowChangePasswordDialog -> {
                            showChangePasswordDialog()
                        }
                        is ProfileUiEvent.ShowLogoutConfirmation -> {
                            showLogoutConfirmation()
                        }
                        is ProfileUiEvent.ProfileUpdated -> {
                            // Optional: Refresh profile info
                        }
                        is ProfileUiEvent.PasswordChanged -> {
                            // Optional: Do something special
                        }
                    }
                }
            }
        }
    }

    private fun showEditProfileDialog() {
        val currentProfile = viewModel.state.value.profile
        
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit_profile, null)
        val edtFullName = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.edt_full_name)
        val edtEmail = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.edt_email)
        val edtPhone = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.edt_phone)
        val edtAddress = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.edt_address)

        currentProfile?.let {
            edtFullName.setText(it.fullName)
            edtEmail.setText(it.email)
            edtPhone.setText(it.phone)
            edtAddress.setText(it.address)
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Chỉnh sửa thông tin")
            .setView(view)
            .setNegativeButton("Hủy") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Lưu") { dialog, _ ->
                val fullName = edtFullName.text?.toString()?.trim()
                val email = edtEmail.text?.toString()?.trim()
                val phone = edtPhone.text?.toString()?.trim()
                val address = edtAddress.text?.toString()?.trim()
                viewModel.updateProfile(fullName, email, phone, address)
                dialog.dismiss()
            }
            .show()
    }

    private fun showChangePasswordDialog() {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_change_password, null)
        val edtOldPassword = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.edt_old_password)
        val edtNewPassword = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.edt_new_password)
        val edtConfirmPassword = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.edt_confirm_password)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Đổi mật khẩu")
            .setView(view)
            .setNegativeButton("Hủy") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Đổi") { dialog, _ ->
                val oldPassword = edtOldPassword.text?.toString() ?: ""
                val newPassword = edtNewPassword.text?.toString() ?: ""
                val confirmPassword = edtConfirmPassword.text?.toString() ?: ""

                if (newPassword != confirmPassword) {
                    Toast.makeText(requireContext(), "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.changePassword(oldPassword, newPassword)
                    dialog.dismiss()
                }
            }
            .show()
    }

    private fun showLogoutConfirmation() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Xác nhận đăng xuất")
            .setMessage("Bạn có chắc chắn muốn đăng xuất?")
            .setNegativeButton("Hủy") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Đăng xuất") { dialog, _ ->
                viewModel.onLogout()
                dialog.dismiss()
            }
            .show()
    }

    private fun showClearHistoryConfirmation() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Xóa lịch sử")
            .setMessage("Bạn có chắc chắn muốn xóa toàn bộ lịch sử quét vé?")
            .setNegativeButton("Hủy") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Xóa") { dialog, _ ->
                viewModel.clearScanHistory()
                dialog.dismiss()
            }
            .show()
    }

    private fun navigateToLogin() {
        val rootNavController = (requireActivity().supportFragmentManager
            .findFragmentById(R.id.root_nav_host) as NavHostFragment)
            .navController
        rootNavController.navigate(
            R.id.loginFragment,
            null,
            NavOptions.Builder()
                .setPopUpTo(R.id.root_graph, true)
                .build()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
