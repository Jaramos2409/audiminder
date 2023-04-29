package gg.jrg.audiminder.music_services.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gg.jrg.audiminder.music_services.data.MusicServiceAuthorizationState
import gg.jrg.audiminder.music_services.data.MusicServiceType
import gg.jrg.audiminder.music_services.domain.usecase.MusicServiceUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicServiceViewModel @Inject constructor(
    private val musicServiceUseCases: MusicServiceUseCases
) : ViewModel() {

    private val _spotifyAuthorizationState =
        MutableStateFlow<MusicServiceAuthorizationState>(MusicServiceAuthorizationState.Unauthorized)

    init {
        viewModelScope.launch {
            val areAuthorizationServicesInitialized =
                musicServiceUseCases.areMusicServicesInitializedUseCase()

            if (areAuthorizationServicesInitialized.value) {
                fetchAndUpdateAuthorizationStateOfThisService(MusicServiceType.SPOTIFY)
            } else {
                areAuthorizationServicesInitialized.collect { isInitialized ->
                    if (isInitialized) {
                        fetchAndUpdateAuthorizationStateOfThisService(MusicServiceType.SPOTIFY)
                    }
                }
            }
        }
    }

    private fun fetchAndUpdateAuthorizationStateOfThisService(type: MusicServiceType) {
        viewModelScope.launch {
            refreshAuthorizationStateForThisService(type)

            musicServiceUseCases.getMusicServiceStateUseCase(type)
                .collect { state ->
                    run {
                        returnAuthorizationStateFlowBasedOnType(type).value = state
                    }
                }
        }
    }

    fun authorizeThisService(type: MusicServiceType) {
        viewModelScope.launch {
            musicServiceUseCases.authorizeMusicServiceUseCase(type)
        }
    }

    fun refreshAuthorizationStateForThisService(type: MusicServiceType) {
        viewModelScope.launch {
            musicServiceUseCases.refreshMusicServiceAuthorizationStateUseCase(type)
        }
    }

    fun isThisServiceAuthorized(type: MusicServiceType): Boolean {
        return returnAuthorizationStateFlowBasedOnType(type).value == MusicServiceAuthorizationState.Authorized
    }

    fun unauthorizeThisService(type: MusicServiceType) {
        viewModelScope.launch {
            musicServiceUseCases.unauthorizeMusicServiceUseCase(type)
        }
    }

    private fun returnAuthorizationStateFlowBasedOnType(type: MusicServiceType) =
        when (type) {
            MusicServiceType.SPOTIFY -> _spotifyAuthorizationState
            else -> throw IllegalArgumentException("Unknown AuthServiceType: $type")
        }

}