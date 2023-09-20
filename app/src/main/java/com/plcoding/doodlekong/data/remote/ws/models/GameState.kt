package com.plcoding.doodlekong.data.remote.ws.models

import com.plcoding.doodlekong.data.remote.ws.models.BaseModel
import com.plcoding.doodlekong.util.Constants

data class GameState(
    val drawingPlayer: String,
    val word: String
): BaseModel(Constants.TYPE_GAME_STATE)
