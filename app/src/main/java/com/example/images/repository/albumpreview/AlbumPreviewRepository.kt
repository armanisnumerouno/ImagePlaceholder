package com.example.images.repository.albumpreview

import com.example.images.entities.Album
import com.example.images.repository.BaseRepository
import io.reactivex.Single

abstract class AlbumPreviewRepository: BaseRepository() {

    abstract fun fetchAlbum(albumId: Int): Single<List<Album>>
}