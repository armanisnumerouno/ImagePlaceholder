package com.example.images.view.albumpreview

import com.example.images.view.BaseViewModel

abstract class AlbumPreviewViewModel: BaseViewModel() {

    abstract fun fetchAlbum(albumId: Int)
}