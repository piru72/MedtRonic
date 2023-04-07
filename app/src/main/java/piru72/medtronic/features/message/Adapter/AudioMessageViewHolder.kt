package piru72.medtronic.features.message.Adapter

import android.media.MediaPlayer
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import piru72.medtronic.databinding.CardAudioMessageBinding
import piru72.medtronic.utils.model.AudioMessage
import java.io.File

class AudioMessageViewHolder(private val binding: CardAudioMessageBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(currentGroup: AudioMessage) {


        binding.deleteWebsiteButton.setOnClickListener {


            val storageRef = FirebaseStorage.getInstance().reference
            // Download the file to a local storage location
            val folder = File(itemView.context.filesDir, "MyAudioFolder")
            if (!folder.exists()) {
                folder.mkdirs()
            }
            val outputFile = File(folder, "recording.3gp")

//            val audioRef = storageRef.child("audio/${outputFile.name}")
//            val localFile = File.createTempFile("recording", "3gp")

            val audioUrl = currentGroup.url
            val audioRef = audioUrl?.let { it1 ->
                FirebaseStorage.getInstance().getReferenceFromUrl(
                    it1
                )
            }
            audioRef?.getFile(outputFile)?.addOnSuccessListener {
                // File downloaded successfully
                // Check the contents of the file
                // For example, you could use a MediaPlayer to play the audio
                val mediaPlayer = MediaPlayer()
                mediaPlayer.setDataSource(outputFile.absolutePath)
                mediaPlayer.prepare()
                mediaPlayer.start()

            }?.addOnFailureListener {
                // Handle any errors
                Toast.makeText(itemView.context, "Failed to download audio", Toast.LENGTH_SHORT).show()
            }?.addOnCompleteListener{
                Toast.makeText(itemView.context, "Failed to download audio", Toast.LENGTH_SHORT).show()
            }

        }

    }
}