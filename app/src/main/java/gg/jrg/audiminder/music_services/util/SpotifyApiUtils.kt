package gg.jrg.audiminder.music_services.util

import com.adamratzman.spotify.models.SimpleAlbum
import gg.jrg.audiminder.collections.domain.model.Album


fun SimpleAlbum.mapToAlbum(): Album {
    return Album(
        albumId = this.id,
        name = this.name,
        artist = this.artists.first().name,
        imageFilePath = this.images.first().url,
        serviceId = 1,
        serviceSpecificURI = this.uri.uri
    )
}