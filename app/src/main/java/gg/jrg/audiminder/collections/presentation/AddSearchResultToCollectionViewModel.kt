package gg.jrg.audiminder.collections.presentation

import androidx.lifecycle.ViewModel
import gg.jrg.audiminder.collections.domain.model.Album

class AddSearchResultToCollectionViewModel : ViewModel() {

    private var album = Album()

    fun setAlbum(album: Album) {
        this.album = album
    }

    fun getAlbum(): Album {
        return album

    }
}