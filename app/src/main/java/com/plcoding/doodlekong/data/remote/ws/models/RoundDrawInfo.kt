package com.plcoding.doodlekong.data.remote.ws.models

import com.plcoding.doodlekong.util.Constants


data class RoundDrawInfo(
    val data: List<String>
): BaseModel(Constants.TYPE_CUR_ROUND_DRAW_INFO)
