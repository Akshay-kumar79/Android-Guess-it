package com.plcoding.doodlekong.data.remote.ws.models

import com.plcoding.doodlekong.util.Constants

data class ChatMessage(
    val from: String,
    val roomName: String,
    val message: String,
    val timeStamp: Long
) : BaseModel(Constants.TYPE_CHAT_MESSAGE)
