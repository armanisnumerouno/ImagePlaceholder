package com.example.images.network

import com.example.images.entities.Album
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("/albums")
    fun fetchAlbums(@Query("start") start: Int,
                    @Query("limit") limit: Int): Single<Response<List<Album>>>

    @GET("/photos")
    fun fetchAlbum(@Query("albumId") albumId: Int): Single<Response<List<Album>>>
}