package com.example.images.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.images.R
import com.example.images.view.albumpreview.AlbumPreviewFragment
import com.example.images.view.albumslist.AlbumsListFragment

class MainActivity : AppCompatActivity(), AlbumsListFragment.Listener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showFragment(AlbumsListFragment())
    }

    override fun onAlbumItemClicked(albumId: Int) {
        showFragment(AlbumPreviewFragment.newInstance(albumId))
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, fragment)
            .addToBackStack(null)
            .commit()
    }
}
