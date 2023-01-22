package piru72.medtronic

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import piru72.medtronic.LoginSignup.SignInActivity
import piru72.medtronic.LoginSignup.SignUpActivity
import piru72.medtronic.databinding.ActivityHomeBinding
import piru72.medtronic.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding .inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}