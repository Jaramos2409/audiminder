package gg.jrg.audiminder.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gg.jrg.audiminder.collections.domain.model.Album
import gg.jrg.audiminder.core.util.logChanges
import gg.jrg.audiminder.music_services.domain.model.SpotifyAuthorizationManager
import gg.jrg.audiminder.music_services.domain.usecase.spotify.SpotifyAuthorizationUseCases
import gg.jrg.audiminder.music_services.domain.usecase.spotify.SpotifySearchUseCases
import gg.jrg.audiminder.music_services.domain.usecase.spotify.SpotifyUserDetailsUseCases
import gg.jrg.audiminder.music_services.util.SpotifyApiStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    spotifyAuthorizationUseCases: SpotifyAuthorizationUseCases,
    spotifyUserDetailsUseCases: SpotifyUserDetailsUseCases,
    spotifySearchUseCases: SpotifySearchUseCases
) : ViewModel() {

    private val _spotifyAuthorizationManager =
        SpotifyAuthorizationManager(
            spotifyAuthorizationUseCases
        )

    private val _query = MutableStateFlow("").apply { logChanges("SearchViewModel _query") }

    private val _albumSearchResults =
        MutableStateFlow<List<Album>>(emptyList()).apply { logChanges("SearchViewModel _albumSearchResults") }
    val albumSearchResults: StateFlow<List<Album>>
        get() = _albumSearchResults

    private val _searchResultsLoadingStatus = MutableStateFlow(SpotifyApiStatus.DONE)
    val searchResultsLoadingStatus: StateFlow<SpotifyApiStatus>
        get() = _searchResultsLoadingStatus

    private val _shouldShowAuthorizationBottomSheet = MutableSharedFlow<Boolean>()
    val showAuthorizationBottomSheet: SharedFlow<Boolean>
        get() = _shouldShowAuthorizationBottomSheet

    init {
        viewModelScope.launch {
            if (isSpotifyAuthorized()) {
                spotifyUserDetailsUseCases.refreshUserDataSuspendUseCase()

                _query
                    .debounce(300)
                    .distinctUntilChanged()
                    .conflate()
                    .flatMapLatest { query ->
                        if (query.isBlank()) {
                            flow { emit(emptyList()) }
                        } else {
                            _searchResultsLoadingStatus.value = SpotifyApiStatus.LOADING
                            spotifySearchUseCases.searchSpotifyAndFetchResultsSuspendUseCase(query)
                                .apply {
                                    _searchResultsLoadingStatus.value = SpotifyApiStatus.DONE
                                }
                        }
                    }
                    .catch { e ->
                        _searchResultsLoadingStatus.value = SpotifyApiStatus.ERROR
                        Timber.e(e)
                    }
                    .collect { results ->
                        _albumSearchResults.value = results
                    }
            }
        }
    }

    fun isSpotifyAuthorized(): Boolean {
        return _spotifyAuthorizationManager.isAuthorized()
    }

    fun setQuery(query: String) {
        if (isSpotifyAuthorized()) {
            _query.value = query
        } else {
            viewModelScope.launch {
                _shouldShowAuthorizationBottomSheet.emit(true)
            }
        }
    }
}