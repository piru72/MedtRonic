package piru72.medtronic.features.message.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import piru72.medtronic.databinding.CardAudioMessageBinding
import piru72.medtronic.utils.model.AudioMessage

class MessageAdapter : RecyclerView.Adapter<AudioMessageViewHolder>() {
    private val _itemList = ArrayList<AudioMessage>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioMessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardAudioMessageBinding.inflate(inflater, parent, false)
        return AudioMessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AudioMessageViewHolder, position: Int) {
        val currentGroup = _itemList[position]
        holder.bind(currentGroup)
    }

    override fun getItemCount(): Int {
        return _itemList.size
    }

    fun updateUserGroupList(groupData: List<AudioMessage>) {

        this._itemList.clear()
        this._itemList.addAll(groupData)
        notifyDataSetChanged()
    }
}