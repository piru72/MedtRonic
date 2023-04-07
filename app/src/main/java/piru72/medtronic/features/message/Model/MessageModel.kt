package piru72.medtronic.features.message.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import piru72.medtronic.features.message.Repository.MessageRepo
import piru72.medtronic.utils.model.AudioMessage

class MessageModel : ViewModel() {

    private val repository: MessageRepo = MessageRepo().getInstance()
    private val _allUserGroups = MutableLiveData<List<AudioMessage>>()
    val allUserGroups: LiveData<List<AudioMessage>> = _allUserGroups

    init {
        repository.loadUserGroups(_allUserGroups)
    }

}