package com.plcoding.doodlekong.data.remote.ws.models

import com.plcoding.doodlekong.data.remote.ws.models.BaseModel
import com.plcoding.doodlekong.util.Constants

data class NewWords(
    val newWords: List<String>
): BaseModel(Constants.TYPE_NEW_WORDS)
