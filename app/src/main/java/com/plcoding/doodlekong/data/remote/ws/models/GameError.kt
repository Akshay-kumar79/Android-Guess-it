package com.plcoding.doodlekong.data.remote.ws.models

import com.plcoding.doodlekong.data.remote.ws.models.BaseModel
import com.plcoding.doodlekong.util.Constants

data class GameError(
    val errorType: Int,
): BaseModel(Constants.TYPE_GAME_ERROR){

    companion object{
        const val ERROR_ROOM_NOT_FOUND = 0
    }

}
