package piru72.medtronic.LoginSignup

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import piru72.medtronic.HomeActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import piru72.medtronic.databinding.ActivitySigninBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    private lateinit var database: DatabaseReference
    private var helper = HelperSignInSignUp(this)

    private lateinit var binding : ActivitySigninBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        database = Firebase.database.reference

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Signing into your Account in few seconds...")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.signinBtn.setOnClickListener {

            val email = binding.usersEmail.text.toString()
            val password = binding.usersPassword.text.toString()

            val validityStatus = helper.validateEmailPasswordFormat(email, password, password)

            if (validityStatus == "Valid Data") {
                fireBaseSignIn(email, password)
            } else {
                helper.makeToast(validityStatus)
            }
        }

        binding.goToSignUpPage.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.goToForgotPasswrdFragment.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

    }

    public override fun onStart() {
        super.onStart()

        if (auth.currentUser?.isEmailVerified == true)
            updateUI(auth.currentUser)

    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            Log.i("LoginActivity", "Update UI Called")
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun writeNewUser(userId: String, name: String, email: String?) {
        val user = UserData(name, email)
        database.child("users").child(userId).setValue(user)
    }

    private fun usernameFromEmail(email: String): String {
        return if (email.contains("@")) {
            email.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        } else {
            email
        }
    }

    private fun onAuthSuccess(user: FirebaseUser) {
        val username = usernameFromEmail(user.email!!)
        writeNewUser(user.uid, username, user.email)
    }


    private fun fireBaseSignIn(email: String, password: String) {
        progressDialog.show()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            when {
                task.isSuccessful -> {
                    progressDialog.dismiss()

                    if (auth.currentUser?.isEmailVerified == true) {
                        updateUI(auth.currentUser)
                        onAuthSuccess(task.result?.user!!)
                    } else
                        helper.makeToast("Please verify your email")
                }
                else -> {
                    helper.makeToast("Login unsuccessful wrong email or password")
                    progressDialog.dismiss()
                }
            }
        }


    }
}