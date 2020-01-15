package com.example.images.repository.albumpreview

import com.example.images.entities.Album
import com.example.images.network.Api
import io.reactivex.Single

class AlbumPreviewRepositoryImpl(
    private val api: Api
): AlbumPreviewRepository() {

    override fun fetchAlbum(albumId: Int): Single<List<Album>> {
        return api.fetchAlbum(albumId).subscribeToResponse()
    }
}