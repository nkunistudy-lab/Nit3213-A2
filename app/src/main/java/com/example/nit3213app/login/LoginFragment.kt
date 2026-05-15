package com.example.nit3213app.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.nit3213app.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usernameInput = view.findViewById<EditText>(R.id.usernameInput)
        val passwordInput = view.findViewById<EditText>(R.id.passwordInput)
        val loginButton = view.findViewById<Button>(R.id.loginButton)
        val errorText = view.findViewById<TextView>(R.id.errorText)
        val loadingBar = view.findViewById<ProgressBar>(R.id.loadingBar)

        loginButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()
            viewModel.login(username, password)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.loginState.collect { keypass ->
                        if (keypass != null) {
                            viewModel.resetLoginState()
                            val bundle = Bundle()
                            bundle.putString("keypass", keypass)
                            findNavController().navigate(
                                R.id.action_loginFragment_to_dashboardFragment,
                                bundle
                            )
                        }
                    }
                }
                launch {
                    viewModel.errorState.collect { error ->
                        if (error != null) {
                            errorText.text = error
                            errorText.visibility = View.VISIBLE
                        } else {
                            errorText.visibility = View.GONE
                        }
                    }
                }
                launch {
                    viewModel.isLoading.collect { loading ->
                        loadingBar.visibility = if (loading) View.VISIBLE else View.GONE
                        loginButton.isEnabled = !loading
                    }
                }
            }
        }
    }
}