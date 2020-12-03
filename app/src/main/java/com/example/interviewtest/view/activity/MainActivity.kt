package com.example.interviewtest.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.interviewtest.R
import com.example.interviewtest.databinding.ActivityMainBinding
import com.example.interviewtest.utils.objects.Constants.networkNotFount
import com.example.interviewtest.utils.extensions.snack
import com.example.interviewtest.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val navHostFragment: NavHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.mainNavContainer) as NavHostFragment }
    private val navController: NavController get() = navHostFragment.navController
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainViewModel>()

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController
        observeNetworkStatus()
        observeMessages()
        viewModel.getPostsFromDB()
        viewModel.getPostsFromAPI()
    }

    private fun observeMessages() {
        viewModel.message.observe(this) {
            binding.root.snack(it.peekContent(), Snackbar.LENGTH_LONG)
        }
    }

    private fun observeNetworkStatus() {
        viewModel.networkStatus.observe(this) { isConnected ->
            if (!isConnected) {
                binding.root.snack(networkNotFount, Snackbar.LENGTH_LONG)
            }

        }
    }
}