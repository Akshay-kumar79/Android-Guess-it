package com.plcoding.doodlekong.data.remote.ws.models

import com.plcoding.doodlekong.data.remote.ws.Room
import com.plcoding.doodlekong.util.Constants

data class PhaseChange(
    var phase: Room.Phase?,
    var time: Long,
    val drawingPlayer: String? = null
): BaseModel(Constants.TYPE_PHASE_CHANGE)
