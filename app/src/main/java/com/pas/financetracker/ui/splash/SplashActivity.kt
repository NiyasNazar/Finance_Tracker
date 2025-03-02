package com.pas.financetracker.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import com.pas.financetracker.MainActivity
import com.pas.financetracker.R
import com.pas.financetracker.databinding.ActivityMainBinding
import com.pas.financetracker.databinding.ActivitySplashBinding
import com.pas.financetracker.ui.base.BaseActivity
import com.pas.financetracker.ui.expense_manager.ExpenseManagerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class SplashActivity : BaseActivity<ExpenseManagerViewModel, ActivitySplashBinding>() {
    override fun getLayout() = R.layout.activity_splash


    override fun getViewModelType(): Class<ExpenseManagerViewModel> {
        return ExpenseManagerViewModel::class.java
    }

    override fun bindViews() {
        startAnimations()

    }

    override fun trackSessionData(): LiveData<Boolean>? {
        return null
    }

    private fun startAnimations() {
        val fadeIn = AlphaAnimation(0f, 1f).apply {
            duration = 1500
            fillAfter = true
        }

        val scaleUp = ScaleAnimation(
            0.8f, 1.1f, // Scale X (start, end)
            0.8f, 1.1f, // Scale Y (start, end)
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 1200
            fillAfter = true
        }

        binding.tvAppName.startAnimation(fadeIn)
        binding.tvAppName.startAnimation(scaleUp)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }
}

