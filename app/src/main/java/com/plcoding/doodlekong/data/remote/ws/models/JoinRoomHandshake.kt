package com.plcoding.doodlekong.data.remote.ws.models

import com.plcoding.doodlekong.data.remote.ws.models.BaseModel
import com.plcoding.doodlekong.util.Constants

data class JoinRoomHandshake(
    val userName: String,
    val roomName: String,
    val clientId: String,
): BaseModel(Constants.TYPE_JOIN_ROOM_HANDSHAKE)
