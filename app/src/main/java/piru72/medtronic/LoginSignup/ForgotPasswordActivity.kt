package piru72.medtronic.LoginSignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import piru72.medtronic.R
import piru72.medtronic.databinding.ActivityForgotPasswordBinding
import piru72.medtronic.databinding.ActivityHomeBinding

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding : ActivityForgotPasswordBinding
    private var helper = HelperSignInSignUp(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotPasswordBinding .inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



       binding.btnForgotPasswordSendMail.setOnClickListener {
           val userEmail = binding.recoveryEmail.text.toString()

           val validityStatus = validateEmail(userEmail)

           if (validityStatus == "Valid Data") {

               var mAuth: FirebaseAuth? = null

               mAuth = FirebaseAuth.getInstance()
               mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener { task ->
                   if (task.isSuccessful) {
                       helper.makeToast("Password reset mail sent")
                       val intent = Intent(this, SignInActivity::class.java)
                       startActivity(intent)
                   } else {
                       helper.makeToast("Unsuccessful attempt ")
                   }

               }
               binding.recoveryEmail.setText("")

           } else {
               helper.makeToast(validityStatus)
           }
       }
    }


    private fun validateEmail(email: String): String {
        return if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            "Invalid email format"
        else if (email.length >= 50)
            "Too long characters"
        else
            "Valid Data"
    }
}