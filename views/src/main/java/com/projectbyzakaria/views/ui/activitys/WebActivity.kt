package com.projectbyzakaria.views.ui.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.projectbyzakaria.views.R
import com.projectbyzakaria.views.databinding.ActivityWebBinding
import com.projectbyzakaria.views.utlis.Constent.TITLE_KEY
import com.projectbyzakaria.views.utlis.Constent.URL_KEY

class WebActivity : AppCompatActivity() {
    lateinit var binding:ActivityWebBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolBar.navigationIcon = ContextCompat.getDrawable(this,R.drawable.back_icon2)
        binding.toolBar.setNavigationOnClickListener {
            finish()
        }

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        var title = intent.getStringExtra(TITLE_KEY)
        var url = intent.getStringExtra(URL_KEY)
        binding.toolBar.title = title
        url?.let { binding.webView.loadUrl(it) } ?: binding.root.addView(TextView(this).apply {
            text = "404"
            textSize = 50f
        })



    }
}