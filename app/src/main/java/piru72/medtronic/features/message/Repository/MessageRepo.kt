package piru72.medtronic.features.message.Repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import piru72.medtronic.utils.model.AudioMessage

class MessageRepo {


    val auth = Firebase.auth
    val user = auth.currentUser!!.uid
    private val groupReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("audio_messages")
    private var INSTANCE: MessageRepo? = null

    fun getInstance(): MessageRepo {
        return INSTANCE ?: synchronized(this) {
            val instance = MessageRepo()
            INSTANCE = instance
            instance
        }
    }

    fun loadUserGroups(allGroups: MutableLiveData<List<AudioMessage>>) {

        groupReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val groupList: List<AudioMessage> = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(AudioMessage::class.java)!!
                    }
                    allGroups.postValue(groupList)
                } catch (_: Exception) {

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        groupReference.keepSynced(true)
    }
}