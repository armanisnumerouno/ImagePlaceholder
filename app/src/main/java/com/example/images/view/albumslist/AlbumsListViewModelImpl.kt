package com.example.images.view.albumslist

import com.example.images.repository.albumslist.AlbumsListRepository

class AlbumsListViewModelImpl(
    private val repository: AlbumsListRepository
): AlbumsListViewModel() {

    private var start = 1
    private var limit = 20

    override fun fetchAlbums() {
        repository.fetchAlbums(start, limit).fetchData()
    }
}