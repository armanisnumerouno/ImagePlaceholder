package com.example.images.view.albumpreview

import com.example.images.repository.albumpreview.AlbumPreviewRepository

class AlbumPreviewViewModelImpl(
    private val repository: AlbumPreviewRepository
): AlbumPreviewViewModel() {

    override fun fetchAlbum(albumId: Int) {
        repository.fetchAlbum(albumId).fetchData()
    }
}