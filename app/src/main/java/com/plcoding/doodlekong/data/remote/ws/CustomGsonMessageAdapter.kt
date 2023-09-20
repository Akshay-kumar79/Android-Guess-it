package com.plcoding.doodlekong.data.remote.ws

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.plcoding.doodlekong.data.remote.ws.models.*
import com.plcoding.doodlekong.util.Constants
import com.tinder.scarlet.Message
import com.tinder.scarlet.MessageAdapter
import java.lang.reflect.Type

@Suppress("UNCHECKED_CAST")
class CustomGsonMessageAdapter<T> private constructor(
    val gson: Gson
) : MessageAdapter<T> {
    
    override fun fromMessage(message: Message): T {
        val stringValue = when (message) {
            is Message.Text -> message.value
            is Message.Bytes -> message.value.toString()
        }
        
        val jsonObject = JsonParser.parseString(stringValue).asJsonObject
        val type = when (jsonObject.get("type").asString) {
            Constants.TYPE_CHAT_MESSAGE -> ChatMessage::class.java
            Constants.TYPE_DRAW_DATA -> DrawData::class.java
            Constants.TYPE_ANNOUNCEMENT -> Announcement::class.java
            Constants.TYPE_JOIN_ROOM_HANDSHAKE -> JoinRoomHandshake::class.java
            Constants.TYPE_PHASE_CHANGE -> PhaseChange::class.java
            Constants.TYPE_CHOSEN_WORD -> ChosenWord::class.java
            Constants.TYPE_GAME_STATE -> GameState::class.java
            Constants.TYPE_PING -> Ping::class.java
            Constants.TYPE_DISCONNECT_REQUEST -> DisconnectRequest::class.java
            Constants.TYPE_DRAW_ACTION -> DrawAction::class.java
            Constants.TYPE_CUR_ROUND_DRAW_INFO -> RoundDrawInfo::class.java
            Constants.TYPE_GAME_ERROR -> GameError::class.java
            Constants.TYPE_NEW_WORDS -> NewWords::class.java
            Constants.TYPE_PLAYERS_LIST -> PlayersList::class.java
            else -> BaseModel::class.java
        }
        val obj = gson.fromJson(stringValue, type)
        return obj as T
    }
    
    override fun toMessage(data: T): Message {
        var convertedData = data as BaseModel
        convertedData = when (convertedData.type) {
            Constants.TYPE_CHAT_MESSAGE -> convertedData as ChatMessage
            Constants.TYPE_DRAW_DATA -> convertedData as DrawData
            Constants.TYPE_ANNOUNCEMENT -> convertedData as Announcement
            Constants.TYPE_JOIN_ROOM_HANDSHAKE -> convertedData as JoinRoomHandshake
            Constants.TYPE_PHASE_CHANGE -> convertedData as PhaseChange
            Constants.TYPE_CHOSEN_WORD -> convertedData as ChosenWord
            Constants.TYPE_GAME_STATE -> convertedData as GameState
            Constants.TYPE_PING -> convertedData as Ping
            Constants.TYPE_DISCONNECT_REQUEST -> convertedData as DisconnectRequest
            Constants.TYPE_DRAW_ACTION -> convertedData as DrawAction
            Constants.TYPE_CUR_ROUND_DRAW_INFO -> convertedData as RoundDrawInfo
            Constants.TYPE_GAME_ERROR -> convertedData as GameError
            Constants.TYPE_NEW_WORDS -> convertedData as NewWords
            Constants.TYPE_PLAYERS_LIST -> convertedData as PlayersList
            else -> convertedData
        }
        
        return Message.Text(gson.toJson(convertedData))
    }
    
    class Factory(
        private val gson: Gson
    ) : MessageAdapter.Factory {
        override fun create(type: Type, annotations: Array<Annotation>): MessageAdapter<*> {
            return CustomGsonMessageAdapter<Any>(gson)
        }
        
    }
}