package com.example.images.repository.albumslist

import com.example.images.entities.Album
import com.example.images.repository.BaseRepository
import io.reactivex.Single

abstract class AlbumsListRepository: BaseRepository() {

    abstract fun fetchAlbums(start: Int, limit: Int): Single<List<Album>>
}