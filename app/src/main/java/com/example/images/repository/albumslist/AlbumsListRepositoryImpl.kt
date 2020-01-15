package com.example.images.repository.albumslist

import com.example.images.entities.Album
import com.example.images.network.Api
import io.reactivex.Single

class AlbumsListRepositoryImpl(
    private val api: Api
): AlbumsListRepository() {

    override fun fetchAlbums(start: Int, limit: Int): Single<List<Album>> {
        return api.fetchAlbums(start, limit).subscribeToResponse()
    }
}