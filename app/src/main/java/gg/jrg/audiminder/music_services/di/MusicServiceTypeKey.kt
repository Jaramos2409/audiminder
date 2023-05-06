package gg.jrg.audiminder.music_services.di

import dagger.MapKey
import gg.jrg.audiminder.music_services.data.MusicServiceType

@MapKey
annotation class MusicServiceTypeKey(val value: MusicServiceType)
