package com.plcoding.doodlekong.data.remote.ws.models

import com.plcoding.doodlekong.util.Constants

data class ChosenWord(
    val chosenWord: String,
    val roomName: String
): BaseModel(Constants.TYPE_CHOSEN_WORD)
