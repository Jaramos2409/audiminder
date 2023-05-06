package gg.jrg.audiminder.music_services.data.repositories

import gg.jrg.audiminder.core.util.logChanges
import gg.jrg.audiminder.music_services.data.MusicServiceAuthorizationState
import gg.jrg.audiminder.music_services.data.MusicServiceType
import gg.jrg.audiminder.music_services.data.providers.MusicServiceProvider
import gg.jrg.audiminder.music_services.di.MusicServiceProviderMap
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


interface MusicServiceRepository {
    fun getMusicServiceProvider(type: MusicServiceType): MusicServiceProvider

    fun refreshAuthorizationState(type: MusicServiceType)

    fun getAuthorizationState(type: MusicServiceType): MutableStateFlow<MusicServiceAuthorizationState>

    fun authorize(type: MusicServiceType)

    fun unauthorize(type: MusicServiceType)
}

class MusicServiceRepositoryImpl @Inject constructor(
    @MusicServiceProviderMap private val providersMap: Map<MusicServiceType, @JvmSuppressWildcards MusicServiceProvider>
) : MusicServiceRepository {

    private val _musicServiceProviders =
        MutableStateFlow(providersMap).apply {
            logChanges(
                "_musicServiceProviders"
            )
        }

    override fun getMusicServiceProvider(type: MusicServiceType): MusicServiceProvider {
        return _musicServiceProviders.value[type]
            ?: throw IllegalArgumentException("No service found for type $type")
    }

    override fun refreshAuthorizationState(type: MusicServiceType) {
        return getMusicServiceProvider(type).refreshAuthorizationState()
    }

    override fun getAuthorizationState(type: MusicServiceType): MutableStateFlow<MusicServiceAuthorizationState> {
        return getMusicServiceProvider(type).authorizationState
    }

    override fun authorize(type: MusicServiceType) {
        getMusicServiceProvider(type).authorize()
    }

    override fun unauthorize(type: MusicServiceType) {
        getMusicServiceProvider(type).unauthorize()
    }

}
