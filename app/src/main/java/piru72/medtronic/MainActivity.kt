package piru72.medtronic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import piru72.medtronic.LoginSignup.SignUpActivity
import piru72.medtronic.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding:  ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding .inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.btnGetStarted.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}