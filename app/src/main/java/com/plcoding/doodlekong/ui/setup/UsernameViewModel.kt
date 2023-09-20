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
class UsernameViewModel @Inject constructor(
    private val repository: SetupRepository,
    private val dispatcher: DispatcherProvider
) : ViewModel() {
    
    sealed class SetupEvent {
        object InputEmptyError : SetupEvent()
        object InputTooShortError : SetupEvent()
        object InputTooLongError : SetupEvent()
        
        data class NavigateToSelectRoomEvent(val username: String) : SetupEvent()
    }
    
    private val _setupEvent = MutableSharedFlow<SetupEvent>()
    val setupEvent: SharedFlow<SetupEvent> = _setupEvent
    
    fun validateUsernameAndNavigateToSelectRoom(username: String) {
        viewModelScope.launch(dispatcher.main) {
            val trimmedUsername = username.trim()
            when {
                trimmedUsername.isEmpty() -> {
                    _setupEvent.emit(SetupEvent.InputEmptyError)
                }
                
                trimmedUsername.length < MIN_USERNAME_LENGTH -> {
                    _setupEvent.emit(SetupEvent.InputTooShortError)
                }
                
                trimmedUsername.length > MAX_USERNAME_LENGTH -> {
                    _setupEvent.emit(SetupEvent.InputTooLongError)
                }
                
                else -> {
                    _setupEvent.emit(SetupEvent.NavigateToSelectRoomEvent(trimmedUsername))
                }
            }
        }
    }
    
}