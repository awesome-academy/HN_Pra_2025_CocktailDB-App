package com.sun.cocktaildb.screen.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sun.cocktaildb.R
import com.sun.cocktaildb.data.model.User
import com.sun.cocktaildb.data.repository.AuthRepository
import com.sun.cocktaildb.data.repository.UserRepository
import com.sun.cocktaildb.data.repository.impl.FirebaseAuthImplement
import com.sun.cocktaildb.data.repository.impl.UserRepositoryImpl
import com.sun.cocktaildb.screen.authenticate.login.LoginActivity
import com.sun.cocktaildb.utils.base.BaseFragment
import com.sun.cocktaildb.utils.dialog.LoadingDialog

/**
 * Profile Fragment for displaying and editing user profile information
 * Implements MVP pattern with Firebase Firestore integration
 */
class ProfileFragment :
    BaseFragment(),
    ProfileView {
    // UI Components
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var tvEditProfile: TextView
    private lateinit var btnLogout: Button

    // Presenter and dependencies
    private lateinit var presenter: ProfilePresenter
    private lateinit var authRepository: AuthRepository
    private lateinit var userRepository: UserRepository
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = inflater.inflate(R.layout.fragment_profile, container, false)

    override fun initView() {
        // Initialize dependencies
        authRepository = FirebaseAuthImplement()
        userRepository = UserRepositoryImpl()
        presenter = ProfilePresenter(authRepository, userRepository)
        presenter.setView(this)

        // Initialize UI components
        initializeViews()
        setupClickListeners()

        // Load user profile
        presenter.loadUserProfile()
    }

    /**
     * Initialize UI components
     */
    private fun initializeViews() {
        view?.let { view ->
            etName = view.findViewById(R.id.et_name)
            etEmail = view.findViewById(R.id.et_email)
            etPhone = view.findViewById(R.id.et_phone)
            tvEditProfile = view.findViewById(R.id.tv_edit_profile)
            btnLogout = view.findViewById(R.id.btn_logout)
        }

        loadingDialog = LoadingDialog(requireContext())
    }

    /**
     * Setup click listeners for interactive elements
     */
    private fun setupClickListeners() {
        tvEditProfile.setOnClickListener {
            presenter.toggleEditMode()
        }

        btnLogout.setOnClickListener {
            presenter.logout()
        }

        // Add text change listeners to update presenter when fields change
        etName.addTextChangedListener(
            object : android.text.TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {}

                override fun afterTextChanged(s: android.text.Editable?) {
                    if (presenter.isInEditMode()) {
                        presenter.updateProfileFields(
                            s.toString(),
                            etPhone.text.toString(),
                        )
                    }
                }
            },
        )

        etPhone.addTextChangedListener(
            object : android.text.TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {}

                override fun afterTextChanged(s: android.text.Editable?) {
                    if (presenter.isInEditMode()) {
                        presenter.updateProfileFields(
                            etName.text.toString(),
                            s.toString(),
                        )
                    }
                }
            },
        )
    }

    override fun displayUserProfile(user: User) {
        // Display user information in UI fields
        etName.setText(user.name)
        etEmail.setText(user.email)
        etPhone.setText(user.phoneNumber)
    }

    override fun setEditMode(isEditMode: Boolean) {
        // Enable/disable editing of profile fields
        etName.isEnabled = isEditMode
        etPhone.isEnabled = isEditMode

        // Update edit profile text
        tvEditProfile.text = if (isEditMode) getString(R.string.save) else getString(R.string.edit_profile)

        // Show visual feedback
        if (isEditMode) {
            etName.requestFocus()
        }
    }

    override fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            showLoading()
        } else {
            hideLoading()
        }
    }

    override fun showSuccess(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        loadingDialog.show()
    }

    override fun hideLoading() {
        loadingDialog.hide()
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun navigateToLogin() {
        // Navigate to login screen
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
