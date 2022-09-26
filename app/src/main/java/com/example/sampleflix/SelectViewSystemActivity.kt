package com.example.sampleflix

import android.content.Intent
import android.os.Bundle
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import com.example.sampleflix.android_view.AndroidViewMainActivity
import com.example.sampleflix.compose.ComposeMainActivity
import com.example.sampleflix.databinding.ActivitySelectViewSystemBinding
import com.wada811.databinding.withBinding

class SelectViewSystemActivity: AppCompatActivity(R.layout.activity_select_view_system) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        withBinding<ActivitySelectViewSystemBinding> { binding ->
            binding.androidViewClickListener = OnClickListener {
                Intent(this, AndroidViewMainActivity::class.java).also {
                    startActivity(it)
                }
            }

            binding.composeClickListener = OnClickListener {
                Intent(this, ComposeMainActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }
}