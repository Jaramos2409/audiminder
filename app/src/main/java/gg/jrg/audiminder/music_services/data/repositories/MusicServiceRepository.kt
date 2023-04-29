package gg.jrg.audiminder.music_services.data.repositories

import gg.jrg.audiminder.core.util.logChanges
import gg.jrg.audiminder.music_services.data.MusicServiceAuthorizationState
import gg.jrg.audiminder.music_services.data.MusicServiceType
import gg.jrg.audiminder.music_services.data.services.MusicServiceProvider
import gg.jrg.audiminder.music_services.data.source.SupportedMusicServiceDao
import gg.jrg.audiminder.music_services.di.MusicServiceProviderMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


interface MusicServiceRepository {
    fun getMusicServiceProvider(type: MusicServiceType): MusicServiceProvider
    fun refreshAuthorizationState(type: MusicServiceType)

    fun getAuthorizationState(type: MusicServiceType): StateFlow<MusicServiceAuthorizationState>

    suspend fun authorize(type: MusicServiceType)

    suspend fun unauthorize(type: MusicServiceType)

    val areMusicServicesInitialized: StateFlow<Boolean>
}

class MusicServiceRepositoryImpl @Inject constructor(
    private val supportedMusicServiceDao: SupportedMusicServiceDao,
    @MusicServiceProviderMap private val providersMap: Map<String, @JvmSuppressWildcards MusicServiceProvider>
) : MusicServiceRepository {

    private val _musicServiceProviders =
        MutableStateFlow<Map<MusicServiceType, MusicServiceProvider>>(emptyMap()).apply { logChanges() }

    override val areMusicServicesInitialized: StateFlow<Boolean> =
        _musicServiceProviders.map { musicServiceProvider ->
            musicServiceProvider.isNotEmpty()
        }.stateIn(CoroutineScope(Dispatchers.IO), SharingStarted.WhileSubscribed(), false)

    init {
        initMusicServices()
    }

    private fun initMusicServices() {
        CoroutineScope(Dispatchers.IO).launch {
            supportedMusicServiceDao.getAllSupportedMusicServices()
                .map { supportedServices ->
                    supportedServices.mapNotNull { dto ->
                        val musicServiceType =
                            MusicServiceType.values().find { it.serviceAsString == dto.serviceName }

                        musicServiceType?.let { type ->
                            val service = providersMap[type.serviceAsString]
                            service?.let { type to service }
                        }
                    }.toMap()
                }
                .collect { serviceProvidersMap ->
                    _musicServiceProviders.value = serviceProvidersMap
                }
        }
    }

    override fun getMusicServiceProvider(type: MusicServiceType): MusicServiceProvider {
        return _musicServiceProviders.value[type]
            ?: throw IllegalArgumentException("No service found for type $type")
    }

    override fun refreshAuthorizationState(type: MusicServiceType) {
        return getMusicServiceProvider(type).refreshAuthorizationState()
    }

    override fun getAuthorizationState(type: MusicServiceType): StateFlow<MusicServiceAuthorizationState> {
        return getMusicServiceProvider(type).authorizationState
    }

    override suspend fun authorize(type: MusicServiceType) {
        getMusicServiceProvider(type).authorize()
    }

    override suspend fun unauthorize(type: MusicServiceType) {
        getMusicServiceProvider(type).unauthorize()
    }

}
