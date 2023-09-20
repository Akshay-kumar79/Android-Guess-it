package com.plcoding.doodlekong.ui.setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.doodlekong.data.remote.ws.Room
import com.plcoding.doodlekong.domain.repository.SetupRepository
import com.plcoding.doodlekong.util.Constants.MAX_ROOM_NAME_LENGTH
import com.plcoding.doodlekong.util.Constants.MAX_USERNAME_LENGTH
import com.plcoding.doodlekong.util.Constants.MIN_ROOM_NAME_LENGTH
import com.plcoding.doodlekong.util.Constants.MIN_USERNAME_LENGTH
import com.plcoding.doodlekong.util.DispatcherProvider
import com.plcoding.doodlekong.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateRoomViewModel @Inject constructor(
    private val repository: SetupRepository,
    private val dispatcher: DispatcherProvider
) : ViewModel() {
    
    sealed class SetupEvent {
        object InputEmptyError : SetupEvent()
        object InputTooShortError : SetupEvent()
        object InputTooLongError : SetupEvent()
        
        data class CreateRoomEvent(val room: Room) : SetupEvent()
        data class CreateRoomErrorEvent(val error: String) : SetupEvent()
        
        data class JoinRoomEvent(val roomName: String) : SetupEvent()
        data class JoinRoomErrorEvent(val error: String) : SetupEvent()
    }
    
    private val _setupEvent = MutableSharedFlow<SetupEvent>()
    val setupEvent: SharedFlow<SetupEvent> = _setupEvent
    
    fun createRoom(room: Room) {
        viewModelScope.launch(dispatcher.main) {
            val trimmedRoomName = room.name.trim()
            when {
                trimmedRoomName.isEmpty() -> {
                    _setupEvent.emit(SetupEvent.InputEmptyError)
                }
                
                trimmedRoomName.length < MIN_ROOM_NAME_LENGTH -> {
                    _setupEvent.emit(SetupEvent.InputTooShortError)
                }
                
                trimmedRoomName.length > MAX_ROOM_NAME_LENGTH -> {
                    _setupEvent.emit(SetupEvent.InputTooLongError)
                }
                
                else -> {
                    val result = repository.createRoom(room)
                    when (result) {
                        is Resource.Success -> _setupEvent.emit(SetupEvent.CreateRoomEvent(room))
                        is Resource.Error -> _setupEvent.emit(SetupEvent.CreateRoomErrorEvent(result.message ?: return@launch))
                    }
                }
            }
        }
    }
    
    fun joinRoom(username: String, roomName: String) {
        viewModelScope.launch(dispatcher.main) {
            val result = repository.joinRoom(username, roomName)
            when (result) {
                is Resource.Success -> _setupEvent.emit(SetupEvent.JoinRoomEvent(roomName))
                is Resource.Error -> _setupEvent.emit(SetupEvent.JoinRoomErrorEvent(result.message ?: return@launch))
            }
        }
    }
    
}