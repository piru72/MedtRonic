package piru72.medtronic.HomeTab

import android.Manifest
import android.Manifest.permission.RECORD_AUDIO
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

import piru72.medtronic.R
import piru72.medtronic.databinding.ActivitySendPulseBinding

import java.io.File


class SendPulseActivity : AppCompatActivity() {
    private lateinit var recorder: MediaRecorder
    private val RECORD_AUDIO_PERMISSION_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySendPulseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                RECORD_AUDIO_PERMISSION_CODE
            )
        }

        binding.textViewUserName.text = "PARVEZ AHAMMED"
        binding.buttonSendAudio.setOnClickListener {
            val rootLayout = layoutInflater.inflate(R.layout.popup_audio_recording, null)
            val label = rootLayout.findViewById<TextView>(R.id.textviewRecordingState)
            val startRecording = rootLayout.findViewById<Button>(R.id.buttonStartRecording)
            val stopRecording = rootLayout.findViewById<Button>(R.id.buttonStopRecording)
            val sendRecording = rootLayout.findViewById<Button>(R.id.buttonSendRecording)
            val hearRecording = rootLayout.findViewById<Button>(R.id.buttonHearRecording)
            var isRecording = false
            recorder = MediaRecorder()

            val popupWindow = PopupWindow(
                rootLayout,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true
            )
            popupWindow.update()
            popupWindow.elevation = 20.5F
            popupWindow.showAtLocation(

                binding.root, // Location to display popup window
                Gravity.CENTER, // Exact position of layout to display popup
                0, // X offset
                -500// Y offset
            )
            startRecording .setOnClickListener {
                label.text = "RECORDING......."
                Toast.makeText(applicationContext, "Recording Started", Toast.LENGTH_SHORT).show()
                if (!isRecording) {
                    startRecording()
                    isRecording = true
                }
            }
            stopRecording.setOnClickListener {
                label.text = "STOPPED"
                Toast.makeText(applicationContext, "Recording stopped", Toast.LENGTH_SHORT).show()
                if (isRecording) {
                    stopRecording()
                    isRecording = false
                }
            }

            sendRecording.setOnClickListener {

                label.text = "SENDING......"
                val storageRef = FirebaseStorage.getInstance().reference
                val folder = File(filesDir, "MyAudioFolder")
                if (!folder.exists()) {
                    folder.mkdirs()
                }
                val outputFile = File(folder, "recording.3gp")

                val audioRef = storageRef.child("audio/${outputFile.name}")



                val uploadTask = audioRef.putFile(Uri.fromFile(outputFile))

                uploadTask.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        audioRef.downloadUrl.addOnSuccessListener { uri ->
                            val audioUrl = uri.toString()
                            Toast.makeText(applicationContext, "Audio uploaded to firebase!!", Toast.LENGTH_SHORT).show()
                            label.text = "SENT"
                            hearRecording.visibility = View.VISIBLE




                        }
                    } else {
                        Toast.makeText(applicationContext, "Failed to upload audio", Toast.LENGTH_SHORT).show()
                    }
                }



            }

            hearRecording.setOnClickListener {
                label.text = "PLAYING......"
                // Create a reference to the file to download
                //val storageRef = FirebaseStorage.getInstance().reference
                //val audioRef = storageRef.child("audio/recording.3gp")
                val storageRef = FirebaseStorage.getInstance().reference
                // Download the file to a local storage location
                val folder = File(filesDir, "MyAudioFolder")
                if (!folder.exists()) {
                    folder.mkdirs()
                }
                val outputFile = File(folder, "recording.3gp")

                val audioRef = storageRef.child("audio/${outputFile.name}")
                val localFile = File.createTempFile("recording", "3gp")
                audioRef.getFile(localFile)
                    .addOnSuccessListener {
                        // File downloaded successfully
                        // Check the contents of the file
                        // For example, you could use a MediaPlayer to play the audio
                        val mediaPlayer = MediaPlayer()
                        mediaPlayer.setDataSource(localFile.absolutePath)
                        mediaPlayer.prepare()
                        mediaPlayer.start()

                    }
                    .addOnFailureListener {
                        // Handle any errors
                        Toast.makeText(applicationContext, "Failed to download audio", Toast.LENGTH_SHORT).show()
                    }.addOnCompleteListener{
                        label.text = "START RECORDING"
                    }


            }
        }
    }

    private fun startRecording() {
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        val folder = File(filesDir, "MyAudioFolder")
        if (!folder.exists()) {
            folder.mkdirs()
        }
        val outputFile = File(folder, "recording.3gp")

        recorder.setOutputFile(outputFile.absolutePath)
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        recorder.prepare()
        recorder.start()
    }

    private fun stopRecording() {
        recorder.stop()
        recorder.release()
    }




    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RECORD_AUDIO_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Record audio permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Record audio permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}