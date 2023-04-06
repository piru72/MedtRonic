package piru72.medtronic.utils.model

data class AudioMessage(
    val url: String? = null,
    val sender: String? = null,
    val receiver: String? = null,
    val date: String? = null,
    val pushKey: String? = null,

) {
    fun toMap(): Map<String, Any?> {

        return mapOf(
            "url" to url,
            "sender" to sender,
            "receiver" to receiver,
            "date" to date,
            "pushKey" to pushKey

        )
    }

}