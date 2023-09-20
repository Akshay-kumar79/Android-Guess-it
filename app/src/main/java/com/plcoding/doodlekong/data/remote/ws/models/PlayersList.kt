package com.plcoding.doodlekong.data.remote.ws.models

import com.plcoding.doodlekong.util.Constants


data class PlayersList(
    val players: List<PlayerData>
): BaseModel(Constants.TYPE_PLAYERS_LIST)
