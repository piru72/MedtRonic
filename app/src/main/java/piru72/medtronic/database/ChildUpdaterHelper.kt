package piru72.medtronic.database

import piru72.medtronic.utils.model.AudioMessage

class ChildUpdaterHelper {

    private var firebaseDatabase = FirebaseUtils.getDatabaseReference()
    fun writeNewAudioMessage(audioDetails: AudioMessage)
    {

        val audioInfo = audioDetails.toMap()

        val childUpdateRequest = hashMapOf<String, Any>(
            "/audio_messages/${audioDetails.pushKey}" to audioInfo
        )
        firebaseDatabase.updateChildren(childUpdateRequest)
    }

}