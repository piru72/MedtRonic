package piru72.medtronic

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import piru72.medtronic.HomeTab.DoctorZoneActivity
import piru72.medtronic.HomeTab.SendPulseActivity
import piru72.medtronic.HomeTab.SosActivity
import piru72.medtronic.LoginSignup.SignInActivity
import piru72.medtronic.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding .inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(this, SignInActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            this@HomeActivity.finish() // if the activity running has it's own context
            // view.getContext().finish() for fragments etc.
        }

        binding.btnDoctorZone.setOnClickListener{
            val intent = Intent(this, DoctorZoneActivity::class.java)
            startActivity(intent)
        }

        binding.btnSos.setOnClickListener{
            val intent = Intent(this, SosActivity::class.java)
            startActivity(intent)
        }

        binding.btnSendPulse.setOnClickListener{
            val intent = Intent(this, SendPulseActivity::class.java)
            startActivity(intent)
        }

    }
}