package com.example.images.view.albumslist

import com.example.images.view.BaseViewModel

abstract class AlbumsListViewModel: BaseViewModel() {

    abstract fun fetchAlbums()
}