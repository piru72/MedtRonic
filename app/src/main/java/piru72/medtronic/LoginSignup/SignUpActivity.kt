package piru72.medtronic.LoginSignup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import piru72.medtronic.databinding.ActivitySignupBinding


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var helper = HelperSignInSignUp(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.signupBtn.setOnClickListener {
//            val intent = Intent(this, SignInActivity::class.java)
//            startActivity(intent)

            val email = binding.usersEmail.text.toString()
            val password = binding.usersPasswordType.text.toString()
            val passwordRetype = binding.usersPasswordRetype.text.toString()
            val name = binding.usersName.text.toString()

            val validityStatus = helper.validateEmailPasswordFormat(email, password, passwordRetype,name)

            if (validityStatus == "Valid Data") {

                if (fireBaseSignup(email, password)) {
                    val intent = Intent(this, SignInActivity::class.java)
                    startActivity(intent)
                }

            } else {
                helper.makeToast(validityStatus)
                binding.usersPasswordRetype.setText("")
            }
        }

        binding.goToSignInPage.setOnClickListener {

            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fireBaseSignup(email: String, password: String): Boolean {
        var accountCreationStatus = false
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

            accountCreationStatus = if (it.isSuccessful) {

                sendVerificationMail()
                helper.makeToast("Account created with email $email")
                true

            } else {
                val exceptionMessage =
                    it.exception.toString().substring(it.exception.toString().indexOf(":") + 1)
                        .trim()
                helper.makeToast(exceptionMessage)
                false
            }
        }

        return accountCreationStatus
    }

    private fun sendVerificationMail() {

        firebaseAuth.currentUser?.sendEmailVerification()?.addOnCompleteListener { task ->

            if (task.isSuccessful) {
                helper.makeToast("Verification mail has been sent on the email ")
                helper.makeToast("CHECK YOUR EMAILS SPAM BOX FOR VERIFICATION EMAIL")
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            } else {

                helper.makeToast("Error Occurred")
            }
        }
    }

}