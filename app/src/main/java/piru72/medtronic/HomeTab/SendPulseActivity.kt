package piru72.medtronic.HomeTab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.PopupWindow
import piru72.medtronic.R
import piru72.medtronic.databinding.ActivitySendPulseBinding

class SendPulseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySendPulseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewUserName.text = "PARVEZ AHAMMED"
        binding.buttonSendAudio.setOnClickListener {
            val rootLayout = layoutInflater.inflate(R.layout.popup_audio_recording, null)

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
        }
    }
}